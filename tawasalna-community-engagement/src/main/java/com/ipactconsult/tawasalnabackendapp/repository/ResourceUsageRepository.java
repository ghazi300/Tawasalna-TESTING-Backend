package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.ResourceUsage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceUsageRepository extends MongoRepository<ResourceUsage, String> {
}
