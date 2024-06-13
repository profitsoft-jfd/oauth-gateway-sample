package dev.profitsoft.jfd.gatewaysample.gateway.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class UserSession {

  @Id
  private String id;
  private String email;
  private String name;
  private Instant expiresAt;

  public boolean isExpired() {
    return expiresAt.isBefore(Instant.now());
  }

}
