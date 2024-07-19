package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingAllocationRepository extends MongoRepository<ParkingAllocation, String>  {



    @Query("{ 'parkingsubSpace.subSpaceId': ?0 }")
    List<ParkingAllocation> findByParkingSubSpaceId(String subSpaceId);
}
