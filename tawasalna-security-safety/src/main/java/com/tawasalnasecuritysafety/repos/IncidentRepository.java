package com.tawasalnasecuritysafety.repos;


import com.tawasalnasecuritysafety.models.Incident;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends MongoRepository<Incident,String> {
}
