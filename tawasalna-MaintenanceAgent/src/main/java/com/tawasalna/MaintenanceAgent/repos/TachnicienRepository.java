package com.tawasalna.MaintenanceAgent.repos;

import com.tawasalna.MaintenanceAgent.models.Technicien;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TachnicienRepository extends MongoRepository<Technicien,String> {
}
