package dev.profitosft.jfd.gatewaysample.contract.service;

import dev.profitosft.jfd.gatewaysample.contract.data.ContractData;
import dev.profitosft.jfd.gatewaysample.contract.dto.ContractSaveDto;
import dev.profitosft.jfd.gatewaysample.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ContractService {

  private final ContractRepository contractRepository;

  public String create(ContractSaveDto dto) {
    ContractData data = new ContractData();
    // TODO validate that the customer exists
    copyToData(dto, data);
    ContractData saved = contractRepository.save(data);
    return saved.getId();
  }

  private void copyToData(ContractSaveDto dto, ContractData data) {
    data.setNumber(dto.getNumber());
    data.setSignDate(dto.getSignDate().atStartOfDay().toInstant(ZoneOffset.UTC));
    data.setCustomerId(new ObjectId(dto.getCustomerId()));
  }

}
