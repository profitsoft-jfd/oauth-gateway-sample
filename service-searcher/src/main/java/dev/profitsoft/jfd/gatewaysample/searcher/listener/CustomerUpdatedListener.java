package dev.profitsoft.jfd.gatewaysample.searcher.listener;

import dev.profitsoft.jfd.gatewaysample.searcher.messaging.CustomerUpdatedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static dev.profitsoft.jfd.gatewaysample.searcher.config.RabbitConfig.QUEUE_CUSTOMER_UPDATED;

@Component
public class CustomerUpdatedListener {

  @RabbitListener(queues = QUEUE_CUSTOMER_UPDATED)
  public void onCustomerUpdated(CustomerUpdatedMessage message) {
    System.out.println("Customer with id " + message.getId() + " was " + (message.isCreated() ? "created" : "updated"));
  }

}
