package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.WaterSource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WaterSourceRepository  extends MongoRepository<WaterSource, String> {
}