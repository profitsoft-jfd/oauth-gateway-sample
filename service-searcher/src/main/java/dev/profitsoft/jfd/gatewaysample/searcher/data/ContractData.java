package dev.profitsoft.jfd.gatewaysample.searcher.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Getter
@Setter
@Document(indexName="contracts")
public class ContractData {

  @Id
  private String id;

  @Field(type = FieldType.Keyword)
  private String number;

  @Field(type = FieldType.Keyword)
  private String customerId;

  @Field(type = FieldType.Text)
  private String customerName;

  @Field(type = FieldType.Text)
  private String customerPhoneNumber;

  @Field(type = FieldType.Date)
  private Instant signDate;

}
