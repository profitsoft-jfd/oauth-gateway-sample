package dev.profitosft.jfd.gatewaysample.controller;

import dev.profitosft.jfd.gatewaysample.dto.CustomerDetailsDto;
import dev.profitosft.jfd.gatewaysample.dto.CustomerSaveDto;
import dev.profitosft.jfd.gatewaysample.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping
  public String create(@Valid @RequestBody CustomerSaveDto dto) {
    return customerService.create(dto);
  }

  @GetMapping("/{id}")
  public CustomerDetailsDto getDetails(@PathVariable("id") String id) {
    return customerService.getDetails(id);
  }

}
