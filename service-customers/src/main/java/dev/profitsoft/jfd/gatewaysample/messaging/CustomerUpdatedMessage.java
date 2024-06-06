package dev.profitsoft.jfd.gatewaysample.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CustomerUpdatedMessage {

  private String id;

  private String fullName;

  private String phoneNumber;

  private boolean created;

}
