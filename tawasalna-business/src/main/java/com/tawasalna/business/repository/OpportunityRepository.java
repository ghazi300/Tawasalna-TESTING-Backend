package com.tawasalna.business.repository;

import com.tawasalna.business.models.Opportunity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends MongoRepository<Opportunity, String> {
}
