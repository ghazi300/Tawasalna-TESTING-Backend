package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.MaintenanceSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MaintenanceScheduleRepository extends MongoRepository<MaintenanceSchedule, String> {
}
