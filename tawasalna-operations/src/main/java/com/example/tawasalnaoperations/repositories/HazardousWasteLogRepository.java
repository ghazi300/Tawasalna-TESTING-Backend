package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.HazardousWasteLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HazardousWasteLogRepository  extends MongoRepository<HazardousWasteLog, String> {
}
