package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSubSpaceRepository extends MongoRepository<ParkingSubSpace, String> {
    @Query("{ 'parkingSpaceId' : ?0 }")
    void deleteByParkingSpaceId(String parkingSpaceId);
}
