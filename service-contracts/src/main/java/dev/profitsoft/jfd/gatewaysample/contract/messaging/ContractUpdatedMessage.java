package dev.profitsoft.jfd.gatewaysample.contract.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Getter
@Builder
@Jacksonized
public class ContractUpdatedMessage {

  private String id;

  private String number;

  private Instant signDate;

  private String customerId;

  private boolean created;

}
