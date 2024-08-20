package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.CulturalProgram;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CulturalProgramRepository extends MongoRepository<CulturalProgram,String> {
}
