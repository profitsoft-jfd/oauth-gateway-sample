package dev.profitsoft.jfd.gatewaysample.searcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceSearcherApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceSearcherApplication.class, args);
  }

}
