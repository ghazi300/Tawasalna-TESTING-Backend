package com.tawasalna.tawasalnacrm.repository;

import com.tawasalna.tawasalnacrm.models.Logo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepository extends MongoRepository<Logo, String> {
}
