package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingViolationRepository extends MongoRepository<ParkingSubSpace, String> {
}
