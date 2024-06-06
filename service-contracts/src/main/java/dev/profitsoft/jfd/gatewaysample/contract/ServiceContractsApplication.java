package dev.profitsoft.jfd.gatewaysample.contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceContractsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceContractsApplication.class, args);
  }

}
