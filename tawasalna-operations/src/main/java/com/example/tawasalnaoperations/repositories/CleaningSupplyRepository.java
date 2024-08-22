package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.CleaningSupply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleaningSupplyRepository extends MongoRepository<CleaningSupply, String> {
}
