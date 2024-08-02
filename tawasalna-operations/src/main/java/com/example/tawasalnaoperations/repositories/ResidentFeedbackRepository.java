package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.ResidentFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ResidentFeedbackRepository extends MongoRepository<ResidentFeedback, String> {
}
