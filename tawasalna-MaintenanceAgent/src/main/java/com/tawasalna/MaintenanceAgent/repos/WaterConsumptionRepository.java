package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.WaterConsumption;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WaterConsumptionRepository  extends MongoRepository<WaterConsumption, String> {
}
