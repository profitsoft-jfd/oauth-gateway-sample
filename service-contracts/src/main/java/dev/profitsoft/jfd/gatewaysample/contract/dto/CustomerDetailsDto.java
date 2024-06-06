package dev.profitsoft.jfd.gatewaysample.contract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@Jacksonized
@JsonInclude(NON_NULL)
public class CustomerDetailsDto {

  private String id;

  private String fullName;

  private String phoneNumber;

}
