package dev.profitosft.jfd.gatewaysample.contract.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Builder
@Jacksonized
public class ContractSaveDto {

  @NotBlank
  private String number;

  @NotNull
  private LocalDate signDate;

  @NotEmpty
  private String customerId;

}
