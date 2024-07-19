package com.tawasalna.business.repository;

import com.tawasalna.business.models.ContractClause;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractClauseRepository extends MongoRepository<ContractClause, String> {
}
