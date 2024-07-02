package com.tawasalna.tawasalnacrm.repository;

import com.tawasalna.tawasalnacrm.models.LiensUtiles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiensUtilesRepository extends MongoRepository<LiensUtiles, String> {
}

