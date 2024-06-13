package dev.profitsoft.jfd.gatewaysample.gateway.filter;

import dev.profitsoft.jfd.gatewaysample.gateway.auth.GoogleAuthenticationService;
import dev.profitsoft.jfd.gatewaysample.gateway.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

  private static final String PREFIX_OAUTH = "/oauth";
  private static final String ENDPOINT_AUTHENTICATE = PREFIX_OAUTH + "/authenticate";
  private static final String ENDPOINT_CALLBACK = PREFIX_OAUTH + "/callback";
  public static final String COOKIE_AUTH_STATE = "auth-state";
  public static final String COOKIE_SESSION_ID = "SESSION-ID";

  private final GoogleAuthenticationService googleAuthenticationService;

  private final SessionService sessionService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    switch (request.getPath().value()) {
      case ENDPOINT_AUTHENTICATE:
        return authenticate(exchange);
      case ENDPOINT_CALLBACK:
        return authCallback(exchange);
    }
    return chain.filter(exchange);
  }

  private Mono<Void> authenticate(ServerWebExchange exchange) {
    String state = UUID.randomUUID().toString();
    addStateCookie(exchange, state);
    String redirectUri = buildRedirectUri(exchange.getRequest());
    String authenticationUrl = googleAuthenticationService.generateAuthenticationUrl(redirectUri, state);
    return sendRedirect(exchange, authenticationUrl);
  }

  private Mono<Void> authCallback(ServerWebExchange exchange) {
    String code = exchange.getRequest().getQueryParams().getFirst("code");
    String state = exchange.getRequest().getQueryParams().getFirst("state");
    String redirectUri = buildRedirectUri(exchange.getRequest());
    return verifyState(state, exchange.getRequest())
        .then(googleAuthenticationService.processAuthenticationCallback(code, redirectUri)
        .doOnNext(userInfo -> log.info("User authenticated: {}", userInfo))
        .flatMap(sessionService::saveSession)
        .flatMap(session -> sessionService.addSessionCookie(exchange, session))
        .then(sendRedirect(exchange, "/api/profile")));
  }

  private Mono<Void> verifyState(String state, ServerHttpRequest request) {
    String cookieState = request.getCookies().getFirst(COOKIE_AUTH_STATE).getValue();
    if (!state.equals(cookieState)) {
      return Mono.error(new IllegalStateException("Invalid state"));
    }
    return Mono.empty();
  }

  private static void addStateCookie(ServerWebExchange exchange, String state) {
    exchange.getResponse().addCookie(ResponseCookie.from(COOKIE_AUTH_STATE)
        .value(state)
        .path(PREFIX_OAUTH)
        .maxAge(Duration.of(30, ChronoUnit.MINUTES))
        .secure(true)
        .build());
  }

  private static Mono<Void> sendRedirect(ServerWebExchange exchange, String location) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.FOUND);
    response.getHeaders().add("Location", location);
    return response.setComplete();
  }

  private String buildRedirectUri(ServerHttpRequest request) {
    String baseUrl = getBaseUrl(request);
    return baseUrl + ENDPOINT_CALLBACK;
  }

  private static String getBaseUrl(ServerHttpRequest request) {
    return request.getURI().toString().substring(0, request.getURI().toString().indexOf(PREFIX_OAUTH));
  }

  @Override
  public int getOrder() {
    return -10;
  }
}
