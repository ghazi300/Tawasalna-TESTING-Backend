package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSubSpaceRepository extends MongoRepository<ParkingSubSpace, String> {
}
