package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.HealthMetric;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HealthMetricRepository extends MongoRepository<HealthMetric, String> {
}
