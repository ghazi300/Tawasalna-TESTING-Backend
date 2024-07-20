package com.example.residentsupportservices.repository;

import com.example.residentsupportservices.entity.Attendance;
import com.example.residentsupportservices.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    Attendance findByEventAndParticipantName(Event event, String participantName);
    List<Attendance> findByEvent(Event event);
}
