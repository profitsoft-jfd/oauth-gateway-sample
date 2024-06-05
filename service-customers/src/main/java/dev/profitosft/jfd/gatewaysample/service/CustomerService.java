package dev.profitosft.jfd.gatewaysample.service;

import dev.profitosft.jfd.gatewaysample.data.CustomerData;
import dev.profitosft.jfd.gatewaysample.dto.CustomerDetailsDto;
import dev.profitosft.jfd.gatewaysample.dto.CustomerSaveDto;
import dev.profitosft.jfd.gatewaysample.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public String create(CustomerSaveDto dto) {
    CustomerData data = new CustomerData();
    copyToData(dto, data);
    return customerRepository.save(data).getId();
  }

  public CustomerDetailsDto getDetails(String id) {
    return customerRepository.findById(id)
        .map(this::copyToDetails)
        .orElseThrow();
  }

  private CustomerDetailsDto copyToDetails(CustomerData customerData) {
    return CustomerDetailsDto.builder()
        .id(customerData.getId())
        .fullName(customerData.getFullName())
        .phoneNumber(customerData.getPhoneNumber())
        .build();
  }

  private void copyToData(CustomerSaveDto dto, CustomerData data) {
    data.setFullName(dto.getFullName());
    data.setPhoneNumber(dto.getPhoneNumber());
  }

}
