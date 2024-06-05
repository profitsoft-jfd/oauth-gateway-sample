package dev.profitosft.jfd.gatewaysample.contract.controller;

import dev.profitosft.jfd.gatewaysample.contract.dto.ContractSaveDto;
import dev.profitosft.jfd.gatewaysample.contract.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

  private final ContractService contractService;

  @PostMapping
  public String create(@Valid @RequestBody ContractSaveDto dto) {
    return contractService.create(dto);
  }

}
