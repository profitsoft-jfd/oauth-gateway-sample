package dev.profitsoft.jfd.gatewaysample.gateway.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class GoogleOauthTokenRequest {

  private String code;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("client_secret")
  private String clientSecret;

  @JsonProperty("grant_type")
  private String grantType;

  @JsonProperty("redirect_uri")
  private String redirectUri;

}
