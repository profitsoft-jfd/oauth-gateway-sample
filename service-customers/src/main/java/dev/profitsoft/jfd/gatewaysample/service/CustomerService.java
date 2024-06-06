package dev.profitsoft.jfd.gatewaysample.service;

import dev.profitsoft.jfd.gatewaysample.data.CustomerData;
import dev.profitsoft.jfd.gatewaysample.dto.CustomerDetailsDto;
import dev.profitsoft.jfd.gatewaysample.dto.CustomerSaveDto;
import dev.profitsoft.jfd.gatewaysample.exception.NotFoundException;
import dev.profitsoft.jfd.gatewaysample.messaging.CustomerUpdatedMessage;
import dev.profitsoft.jfd.gatewaysample.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static dev.profitsoft.jfd.gatewaysample.config.RabbitConfig.EXCHANGE_CUSTOMER_UPDATED;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  private final RabbitTemplate rabbitTemplate;

  public String create(CustomerSaveDto dto) {
    CustomerData data = new CustomerData();
    copyToData(dto, data);
    CustomerData saved = customerRepository.save(data);
    sendCustomerUpdatedMessage(saved, true);
    return saved.getId();
  }

  public void update(String id, CustomerSaveDto dto) {
    CustomerData data = getOrThrow(id);
    copyToData(dto, data);
    CustomerData saved = customerRepository.save(data);
    sendCustomerUpdatedMessage(saved, false);
  }

  private CustomerData getOrThrow(String id) {
    return customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Customer with id '%s' not found".formatted(id)));
  }

  public CustomerDetailsDto getDetails(String id) {
    CustomerData data = getOrThrow(id);
    return copyToDetails(data);
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

  private void sendCustomerUpdatedMessage(CustomerData saved, boolean created) {
    CustomerUpdatedMessage message = CustomerUpdatedMessage.builder()
        .id(saved.getId())
        .fullName(saved.getFullName())
        .phoneNumber(saved.getPhoneNumber())
        .created(created)
        .build();
    rabbitTemplate.convertAndSend(EXCHANGE_CUSTOMER_UPDATED, "", message);
  }

}
