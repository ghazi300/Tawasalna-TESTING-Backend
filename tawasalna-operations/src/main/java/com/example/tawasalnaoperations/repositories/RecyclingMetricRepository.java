package com.example.tawasalnaoperations.repositories;

import com.example.tawasalnaoperations.entities.MaterialType;
import com.example.tawasalnaoperations.entities.RecyclingMetric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingMetricRepository extends MongoRepository<RecyclingMetric, String> {
    List<RecyclingMetric> findByType(MaterialType type);

}
