package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.EnergySource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnergySourceRepository extends MongoRepository<EnergySource, String> {
}
