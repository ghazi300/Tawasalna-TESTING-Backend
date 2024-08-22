package com.tawasalna.tawasalnacrisis.repositories;

import com.tawasalna.tawasalnacrisis.models.PlanUrgence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanUrgenceRepository extends MongoRepository<PlanUrgence, String> {
}

