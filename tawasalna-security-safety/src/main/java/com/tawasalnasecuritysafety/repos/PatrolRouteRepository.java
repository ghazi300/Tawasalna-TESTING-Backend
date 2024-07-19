package com.tawasalnasecuritysafety.repos;

import com.tawasalnasecuritysafety.models.PatrolRoute;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrolRouteRepository extends MongoRepository<PatrolRoute, String> {
}
