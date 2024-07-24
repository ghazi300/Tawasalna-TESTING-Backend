package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends MongoRepository<ParkingSpace, String> {
    @Query(value = "{ 'parkingSpaceId' : ?0 }", fields = "{ 'subSpaces' : 1 }")
    List<ParkingSubSpace> findSubSpacesByParkingSpaceId(String parkingSpaceId);
}
