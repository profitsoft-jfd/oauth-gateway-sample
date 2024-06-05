package dev.profitosft.jfd.gatewaysample.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CustomerSaveDto {

  @NotBlank
  private String fullName;

  private String phoneNumber;

}
