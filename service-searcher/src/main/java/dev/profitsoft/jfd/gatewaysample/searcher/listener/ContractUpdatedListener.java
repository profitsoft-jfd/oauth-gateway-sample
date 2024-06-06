package dev.profitsoft.jfd.gatewaysample.searcher.listener;

import dev.profitsoft.jfd.gatewaysample.searcher.messaging.ContractUpdatedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static dev.profitsoft.jfd.gatewaysample.searcher.config.RabbitConfig.QUEUE_CONTRACT_UPDATED;

@Component
public class ContractUpdatedListener {

  @RabbitListener(queues = QUEUE_CONTRACT_UPDATED)
  public void onContractUpdated(ContractUpdatedMessage message) {
    System.out.println("Contract " + message.getNumber() + " was " + (message.isCreated() ? "created" : "updated"));
  }

}
