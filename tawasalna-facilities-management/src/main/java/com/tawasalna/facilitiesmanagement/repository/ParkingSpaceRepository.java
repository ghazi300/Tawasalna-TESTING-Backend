package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpaceRepository extends MongoRepository<ParkingSpace, String> {
}
