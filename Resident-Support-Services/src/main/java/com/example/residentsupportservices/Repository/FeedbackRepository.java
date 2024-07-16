package com.example.residentsupportservices.repository;

import com.example.residentsupportservices.entity.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}
