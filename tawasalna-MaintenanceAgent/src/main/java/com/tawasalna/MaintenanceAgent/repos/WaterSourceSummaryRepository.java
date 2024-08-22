package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.WaterSourceSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WaterSourceSummaryRepository extends MongoRepository<WaterSourceSummary, String> {
}
