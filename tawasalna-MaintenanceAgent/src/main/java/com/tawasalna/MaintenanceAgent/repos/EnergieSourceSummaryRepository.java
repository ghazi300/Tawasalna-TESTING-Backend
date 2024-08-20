package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.EnergieConsumptionSummary;
import com.tawasalna.MaintenanceAgent.models.EnergySourceSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnergieSourceSummaryRepository extends MongoRepository<EnergySourceSummary, String> {
}
