package dev.profitsoft.jfd.gatewaysample.contract.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  public static final String EXCHANGE_CONTRACT_UPDATED = "contract-updated-exchange";

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public FanoutExchange contractUpdatedExchange()  {
    return new FanoutExchange(EXCHANGE_CONTRACT_UPDATED, true, false);
  }

}
