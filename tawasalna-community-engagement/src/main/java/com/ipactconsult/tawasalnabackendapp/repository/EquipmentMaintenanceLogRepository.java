package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.EquipmentMaintenanceLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipmentMaintenanceLogRepository extends MongoRepository<EquipmentMaintenanceLog,String> {
}
