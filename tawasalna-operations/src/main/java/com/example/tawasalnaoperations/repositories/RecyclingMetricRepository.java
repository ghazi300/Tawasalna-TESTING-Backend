package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.RecyclingMetric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingMetricRepository extends MongoRepository<RecyclingMetric, String> {
}
