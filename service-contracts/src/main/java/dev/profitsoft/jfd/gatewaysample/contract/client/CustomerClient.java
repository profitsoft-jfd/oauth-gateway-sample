package dev.profitsoft.jfd.gatewaysample.contract.client;

import dev.profitsoft.jfd.gatewaysample.contract.dto.CustomerDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "service-customers", dismiss404 = true)
public interface CustomerClient {

  @GetMapping(value = "/api/customers/{id}")
  Optional<CustomerDetailsDto> getCustomer(@PathVariable("id") String id);

}
