package dev.profitsoft.jfd.gatewaysample.searcher.service;

import dev.profitsoft.jfd.gatewaysample.searcher.client.CustomerClient;
import dev.profitsoft.jfd.gatewaysample.searcher.data.ContractData;
import dev.profitsoft.jfd.gatewaysample.searcher.dto.ContractInfoDto;
import dev.profitsoft.jfd.gatewaysample.searcher.dto.CustomerDetailsDto;
import dev.profitsoft.jfd.gatewaysample.searcher.messaging.ContractUpdatedMessage;
import dev.profitsoft.jfd.gatewaysample.searcher.messaging.CustomerUpdatedMessage;
import dev.profitsoft.jfd.gatewaysample.searcher.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

  private final ContractRepository contractRepository;

  private final CustomerClient customerClient;

  public List<ContractInfoDto> searchContracts() {
    log.debug("Searching for contracts");
    // some special full text search logic could be added here, but for this example we just return all contracts
    List<ContractInfoDto> result = new ArrayList<>();
    for (ContractData contractData : contractRepository.findAll()) {
      result.add(toContractInfo(contractData));
    }
    return result;
  }

  private ContractInfoDto toContractInfo(ContractData data) {
    return ContractInfoDto.builder()
        .id(data.getId())
        .number(data.getNumber())
        .signDate(data.getSignDate().atOffset(ZoneOffset.UTC).toLocalDate())
        .customerName(data.getCustomerName())
        .customerPhoneNumber(data.getCustomerPhoneNumber())
        .build();
  }

  public void saveContract(ContractUpdatedMessage message) {
    ContractData data = !message.isCreated()
        ? contractRepository.findById(message.getId())
            .orElseGet(ContractData::new)
        : new ContractData();
    copyToData(message, data);
    contractRepository.save(data);
  }

  public void updateCustomerData(CustomerUpdatedMessage message) {
    if (message.isCreated()) {
      // if it's a new customer, we don't expect to have any contracts, so we can skip the search
      return;
    }
    // the implementation could be more efficient, but for this example it's enough
    contractRepository.findAllByCustomerId(message.getId())
        .forEach(contract -> updateContractFromCustomer(message, contract));
  }

  private void updateContractFromCustomer(CustomerUpdatedMessage message, ContractData contract) {
    contract.setCustomerName(message.getFullName());
    contract.setCustomerPhoneNumber(message.getPhoneNumber());
    contractRepository.save(contract);
  }

  private void copyToData(ContractUpdatedMessage message, ContractData data) {
    data.setId(message.getId());
    data.setNumber(message.getNumber());
    data.setSignDate(message.getSignDate());
    if (data.getCustomerId() == null || !data.getCustomerId().equals(message.getCustomerId())) {
      updateCustomerDetails(data, message.getCustomerId());
    }
  }

  private void updateCustomerDetails(ContractData data, String customerId) {
    try {
      CustomerDetailsDto customer = customerClient.getCustomer(customerId);
      data.setCustomerId(customerId);
      data.setCustomerName(customer.getFullName());
      data.setCustomerPhoneNumber(customer.getPhoneNumber());
    } catch (Exception e) {
      log.error("Failed to get customer details for id '{}'", customerId, e);
    }
  }

}
