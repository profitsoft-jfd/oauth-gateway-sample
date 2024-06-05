package dev.profitosft.jfd.gatewaysample.contract.data;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document("contracts")
public class ContractData {

  private String id;

  private String number;

  private Instant signDate;

  private ObjectId customerId;

}
