package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.FitnessCenterUsage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FitnessCenterUsageRepository extends MongoRepository<FitnessCenterUsage,String> {
}
