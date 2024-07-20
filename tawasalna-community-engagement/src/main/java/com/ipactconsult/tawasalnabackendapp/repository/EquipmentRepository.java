package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipmentRepository extends MongoRepository<Equipment,String> {
}
