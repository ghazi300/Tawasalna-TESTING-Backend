package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.PestControlReport;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PestControlReportRepository extends MongoRepository<PestControlReport, String> {
}
