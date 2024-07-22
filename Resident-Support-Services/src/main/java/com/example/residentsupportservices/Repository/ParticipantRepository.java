package com.example.residentsupportservices.repository;

import com.example.residentsupportservices.entity.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParticipantRepository extends MongoRepository<Participant, String> {
}
