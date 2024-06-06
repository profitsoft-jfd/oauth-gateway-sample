package dev.profitsoft.jfd.gatewaysample.contract.service;

import dev.profitsoft.jfd.gatewaysample.contract.config.RabbitConfig;
import dev.profitsoft.jfd.gatewaysample.contract.data.ContractData;
import dev.profitsoft.jfd.gatewaysample.contract.dto.ContractSaveDto;
import dev.profitsoft.jfd.gatewaysample.contract.messaging.ContractUpdatedMessage;
import dev.profitsoft.jfd.gatewaysample.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ContractService {

  private final ContractRepository contractRepository;

  private final RabbitTemplate rabbitTemplate;

  public String create(ContractSaveDto dto) {
    ContractData data = new ContractData();
    // TODO validate that the customer exists
    copyToData(dto, data);
    ContractData saved = contractRepository.save(data);
    sendContractUpdatedMessage(saved, true);
    return saved.getId();
  }

  private void sendContractUpdatedMessage(ContractData saved, boolean created) {
    ContractUpdatedMessage message = ContractUpdatedMessage.builder()
        .id(saved.getId())
        .number(saved.getNumber())
        .signDate(saved.getSignDate())
        .customerId(saved.getCustomerId().toHexString())
        .created(created)
        .build();
    rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_CONTRACT_UPDATED, "", message);
  }

  private void copyToData(ContractSaveDto dto, ContractData data) {
    data.setNumber(dto.getNumber());
    data.setSignDate(dto.getSignDate().atStartOfDay().toInstant(ZoneOffset.UTC));
    data.setCustomerId(new ObjectId(dto.getCustomerId()));
  }

}
