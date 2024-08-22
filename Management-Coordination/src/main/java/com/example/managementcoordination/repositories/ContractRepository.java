package com.example.managementcoordination.repositories;

import com.example.managementcoordination.entities.Contracts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends MongoRepository<Contracts, String> {
}
