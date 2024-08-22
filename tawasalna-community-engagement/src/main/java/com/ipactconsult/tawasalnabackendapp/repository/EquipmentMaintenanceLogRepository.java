package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.EquipmentMaintenanceLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EquipmentMaintenanceLogRepository extends MongoRepository<EquipmentMaintenanceLog,String> {
    List<EquipmentMaintenanceLog> findByEquipmentId(String equipmentId);
}
