package dev.profitsoft.jfd.gatewaysample.gateway.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class GoogleOauthTokenResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("expires_in")
  private int expiresIn;

  private String scope;
  @JsonProperty("token_type")

  private String tokenType;

  @JsonProperty("id_token")
  private String idToken;

}
