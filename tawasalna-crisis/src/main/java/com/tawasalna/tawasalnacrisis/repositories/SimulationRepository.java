package com.tawasalna.tawasalnacrisis.repositories;

import com.tawasalna.tawasalnacrisis.models.Simulation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimulationRepository extends MongoRepository<Simulation,String> {
}
