package dev.profitsoft.jfd.gatewaysample.searcher.client;

import dev.profitsoft.jfd.gatewaysample.searcher.dto.CustomerDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-customers")
public interface CustomerClient {

  @GetMapping(value = "/api/customers/{id}")
  CustomerDetailsDto getCustomer(@PathVariable("id") String id);

}
