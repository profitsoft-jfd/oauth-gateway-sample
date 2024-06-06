package dev.profitsoft.jfd.gatewaysample.searcher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  public static final String QUEUE_CUSTOMER_UPDATED = "searcher-customer-updated";
  public static final String EXCHANGE_CUSTOMER_UPDATED = "customer-updated-exchange";
  public static final String QUEUE_CONTRACT_UPDATED = "searcher-contract-updated";
  public static final String EXCHANGE_CONTRACT_UPDATED = "contract-updated-exchange";

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue customerUpdatedQueue() {
    return new Queue(QUEUE_CUSTOMER_UPDATED);
  }

  @Bean
  public FanoutExchange customerUpdatedExchange()  {
    return new FanoutExchange(EXCHANGE_CUSTOMER_UPDATED, true, false);
  }

  @Bean
  public Binding customerUpdatedBinding() {
    return BindingBuilder.bind(customerUpdatedQueue()).to(customerUpdatedExchange());
  }

  @Bean
  public Queue contractUpdatedQueue() {
    return new Queue(QUEUE_CONTRACT_UPDATED);
  }

  @Bean
  public FanoutExchange contractUpdatedExchange()  {
    return new FanoutExchange(EXCHANGE_CONTRACT_UPDATED, true, false);
  }

  @Bean
  public Binding contractUpdatedBinding() {
    return BindingBuilder.bind(contractUpdatedQueue()).to(contractUpdatedExchange());
  }

}
