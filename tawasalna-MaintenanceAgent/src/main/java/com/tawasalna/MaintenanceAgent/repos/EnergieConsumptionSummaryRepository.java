package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.EnergieConsumptionSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnergieConsumptionSummaryRepository extends MongoRepository<EnergieConsumptionSummary, String> {
}
