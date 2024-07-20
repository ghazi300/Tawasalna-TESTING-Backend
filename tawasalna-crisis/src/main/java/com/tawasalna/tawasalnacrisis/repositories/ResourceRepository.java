package com.tawasalna.tawasalnacrisis.repositories;

import com.tawasalna.tawasalnacrisis.models.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceRepository extends MongoRepository<Resource, String> {
    // Vous pouvez ajouter des méthodes de recherche personnalisées ici si nécessaire
}
