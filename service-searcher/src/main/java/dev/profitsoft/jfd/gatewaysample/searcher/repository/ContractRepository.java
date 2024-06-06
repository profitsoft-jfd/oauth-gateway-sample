package dev.profitsoft.jfd.gatewaysample.searcher.repository;

import dev.profitsoft.jfd.gatewaysample.searcher.data.ContractData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ContractRepository extends ElasticsearchRepository<ContractData, String> {

  Stream<ContractData> findAllByCustomerId(String customerId);

}
