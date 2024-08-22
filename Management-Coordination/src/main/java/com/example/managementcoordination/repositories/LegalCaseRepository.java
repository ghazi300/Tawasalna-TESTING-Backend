package com.example.managementcoordination.repositories;

import com.example.managementcoordination.entities.LegalCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalCaseRepository  extends MongoRepository<LegalCase,String> {
}
