package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.WaterConsumptionSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WaterConsumptionSummaryRepository  extends MongoRepository<WaterConsumptionSummary, String> {
}
