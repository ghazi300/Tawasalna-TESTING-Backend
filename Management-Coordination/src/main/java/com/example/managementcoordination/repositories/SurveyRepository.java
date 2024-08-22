package com.example.managementcoordination.repositories;

import com.example.managementcoordination.entities.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {
}
