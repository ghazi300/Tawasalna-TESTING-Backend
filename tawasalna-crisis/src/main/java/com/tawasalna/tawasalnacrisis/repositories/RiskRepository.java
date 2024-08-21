package com.tawasalna.tawasalnacrisis.repositories;

import com.tawasalna.tawasalnacrisis.models.Risk;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RiskRepository extends MongoRepository<Risk,String> {
}
