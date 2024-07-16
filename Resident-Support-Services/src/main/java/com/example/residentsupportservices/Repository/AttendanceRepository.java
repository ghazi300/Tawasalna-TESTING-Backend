package com.example.residentsupportservices.repository;

import com.example.residentsupportservices.entity.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
}
