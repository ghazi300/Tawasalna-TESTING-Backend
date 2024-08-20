package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.AsignedTask;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AsignedTaskRepository extends MongoRepository<AsignedTask,String> {
}
