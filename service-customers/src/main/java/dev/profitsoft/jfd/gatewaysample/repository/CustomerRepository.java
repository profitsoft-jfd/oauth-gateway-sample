package dev.profitsoft.jfd.gatewaysample.repository;

import dev.profitsoft.jfd.gatewaysample.data.CustomerData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerData, String>{
}
