package com.example.residentsupportservices.repository;

import com.example.residentsupportservices.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
