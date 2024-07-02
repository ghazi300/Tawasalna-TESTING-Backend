package com.tawasalna.tawasalnacrm.repository;

import com.tawasalna.tawasalnacrm.models.ReseauxSociaux;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReseauxSociauxRepository extends MongoRepository<ReseauxSociaux, String> {
}
