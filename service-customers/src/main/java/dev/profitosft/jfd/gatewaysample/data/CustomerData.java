package dev.profitosft.jfd.gatewaysample.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("customers")
public class CustomerData {

  @Id
  private String id;

  private String fullName;

  private String phoneNumber;

}
