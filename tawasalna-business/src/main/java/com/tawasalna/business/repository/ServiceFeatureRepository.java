package com.tawasalna.business.repository;

import com.tawasalna.business.models.ServiceFeature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceFeatureRepository extends MongoRepository<ServiceFeature,String> {
}
