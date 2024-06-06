package dev.profitsoft.jfd.gatewaysample.searcher.listener;

import dev.profitsoft.jfd.gatewaysample.searcher.messaging.CustomerUpdatedMessage;
import dev.profitsoft.jfd.gatewaysample.searcher.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static dev.profitsoft.jfd.gatewaysample.searcher.config.RabbitConfig.QUEUE_CUSTOMER_UPDATED;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerUpdatedListener {

  private final ContractService contractService;

  @RabbitListener(queues = QUEUE_CUSTOMER_UPDATED)
  public void onCustomerUpdated(CustomerUpdatedMessage message) {
    log.debug("Consumed customer {} message, id={}",
        message.isCreated() ? "created" : "updated",
        message.getId());
    contractService.updateCustomerData(message);
  }

}
