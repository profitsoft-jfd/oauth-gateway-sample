package dev.profitsoft.jfd.gatewaysample.contract.repository;

import dev.profitsoft.jfd.gatewaysample.contract.data.ContractData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends MongoRepository<ContractData, String>{
}
