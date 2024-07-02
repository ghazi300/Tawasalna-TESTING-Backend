package com.tawasalna.business.repository;

import com.tawasalna.business.models.ServiceCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends MongoRepository<ServiceCategory, String> {
     Boolean existsByTitle(String title);
}
