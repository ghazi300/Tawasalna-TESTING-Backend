package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.models.ParkingAllocationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingAllocationRepository extends MongoRepository<ParkingAllocation, String>  {



    @Query("{ 'parkingsubSpace.subSpaceId': ?0 }")
    List<ParkingAllocation> findByParkingSubSpaceId(String subSpaceId);



    List<ParkingAllocation> findAll();
    @Query("{ 'violationStatus' : { $in: ['UNPAID', 'UNDER_REVIEW'] }, 'status' : 'EXPIRED' }")
    List<ParkingAllocation> findViolationParking();

    long countByStatus(ParkingAllocationStatus status);

      List<ParkingAllocation> findAllByParkingsubSpace();
}
