package dev.profitsoft.jfd.gatewaysample.gateway.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import dev.profitsoft.jfd.gatewaysample.gateway.auth.dto.GoogleOauthTokenRequest;
import dev.profitsoft.jfd.gatewaysample.gateway.auth.dto.GoogleOauthTokenResponse;
import dev.profitsoft.jfd.gatewaysample.gateway.auth.dto.UserInfo;
import io.micrometer.common.util.StringUtils;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GoogleAuthenticationService {

  private static final String AUTH_GRANT = "authorization_code";
  private static final int TIMEOUT_SECONDS = 5;
  private static final String GOOGLE_API_BASE_URL = "https://oauth2.googleapis.com";
  private static final String GOOGLE_AUTH_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";

  private String clientId;

  private String clientSecret;

  private String scope;

  private final GoogleIdTokenVerifier tokenVerifier;

  private final WebClient webClient;

  public GoogleAuthenticationService(
      @Value("${oauth.google.clientId}") String clientId,
      @Value("${oauth.google.clientSecret}") String clientSecret,
      @Value("${oauth.google.scope}") String scope,
      WebClient.Builder webClientBuilder
  ) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.scope = scope;
    this.tokenVerifier = buildTokenVerifier();
    this.webClient = buildWebClient(webClientBuilder);
  }

  public String generateAuthenticationUrl(String redirectUri, String state) {
    return GOOGLE_AUTH_BASE_URL +
        "?client_id=" + clientId +
        "&redirect_uri=" + URLEncoder.encode(redirectUri) +
        "&response_type=code" +
        "&scope=" + URLEncoder.encode(scope) +
        "&state=" + state;
  }

  public Mono<UserInfo> processAuthenticationCallback(String code, String redirectUri) {
    return requestOauthTokens(code, redirectUri)
        .flatMap(oauthTokenResponse -> parseAndVerifyIdToken(oauthTokenResponse))
        .map(idToken -> UserInfo.builder()
            .email(idToken.getPayload().getEmail())
            .name((String) idToken.getPayload().get("name"))
            .build());
  }

  private Mono<GoogleIdToken> parseAndVerifyIdToken(GoogleOauthTokenResponse oauthTokenResponse) {
    try {
      if (oauthTokenResponse.getIdToken() == null) {
        return Mono.error(new IllegalArgumentException("ID token was empty"));
      }
      GoogleIdToken result = tokenVerifier.verify(oauthTokenResponse.getIdToken());
      if (result == null) {
        return Mono.error(new IllegalArgumentException("Id token verification failed"));
      }
      if (StringUtils.isEmpty(result.getPayload().getEmail())) {
        return Mono.error(new IllegalArgumentException("Email not found in id token"));
      }
      return Mono.just(result);
    } catch (Exception e) {
      return Mono.error(new IllegalArgumentException("Invalid id token returned by google", e));
    }
  }

  private Mono<GoogleOauthTokenResponse> requestOauthTokens(String code, String redirectUri) {
    GoogleOauthTokenRequest body = GoogleOauthTokenRequest.builder()
        .code(code)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .grantType(AUTH_GRANT)
        .redirectUri(redirectUri)
        .build();
    return webClient.post()
        .uri("/token")
        .body(Mono.just(body), GoogleOauthTokenRequest.class)
        .retrieve()
        .bodyToMono(GoogleOauthTokenResponse.class);
  }

  private GoogleIdTokenVerifier buildTokenVerifier() {
    return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
        .setAudience(List.of(clientId))
        .build();
  }

  private WebClient buildWebClient(WebClient.Builder webClientBuilder) {
    return webClientBuilder
        .baseUrl(GOOGLE_API_BASE_URL)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(new ReactorClientHttpConnector(HttpClient.from(buildTcpClient())))
        .build();
  }

  private static TcpClient buildTcpClient() {
    return TcpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT_SECONDS * 1000)
        .doOnConnected(conn ->
            conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT_SECONDS, TimeUnit.SECONDS)));
  }

}
