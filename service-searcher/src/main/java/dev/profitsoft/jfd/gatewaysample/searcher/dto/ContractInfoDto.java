package dev.profitsoft.jfd.gatewaysample.searcher.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Builder
@Jacksonized
public class ContractInfoDto {

  private String id;

  private String number;

  private LocalDate signDate;

  private String customerName;

  private String customerPhoneNumber;

}
