package com.ipactconsult.tawasalnabackendapp.repository;


import com.ipactconsult.tawasalnabackendapp.models.Milestone;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MileStoneRepository extends MongoRepository<Milestone,String> {
}
