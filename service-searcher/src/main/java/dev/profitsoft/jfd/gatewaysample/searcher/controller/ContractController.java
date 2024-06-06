package dev.profitsoft.jfd.gatewaysample.searcher.controller;

import dev.profitsoft.jfd.gatewaysample.searcher.dto.ContractInfoDto;
import dev.profitsoft.jfd.gatewaysample.searcher.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

  private final ContractService contractService;

  @GetMapping
  public List<ContractInfoDto> searchContracts() {
    return contractService.searchContracts();
  }

}
