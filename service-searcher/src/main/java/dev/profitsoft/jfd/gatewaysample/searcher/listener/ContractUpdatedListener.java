package dev.profitsoft.jfd.gatewaysample.searcher.listener;

import dev.profitsoft.jfd.gatewaysample.searcher.messaging.ContractUpdatedMessage;
import dev.profitsoft.jfd.gatewaysample.searcher.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static dev.profitsoft.jfd.gatewaysample.searcher.config.RabbitConfig.QUEUE_CONTRACT_UPDATED;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractUpdatedListener {

  private final ContractService contractService;

  @RabbitListener(queues = QUEUE_CONTRACT_UPDATED)
  public void onContractUpdated(ContractUpdatedMessage message) {
    log.debug("Consumed contract {} message, contractNumber={}",
        message.isCreated() ? "created" : "updated",
        message.getNumber());
    contractService.saveContract(message);
  }

}
