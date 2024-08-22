package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.Event;
import com.ipactconsult.tawasalnabackendapp.models.StatusEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event,String> {
    List<Event> findByStatus(StatusEvent status);
}
