package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.models.ParkingAllocationStatus;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingAllocationRepository extends MongoRepository<ParkingAllocation, String>  {



    @Query("{ 'parkingsubSpace.subSpaceId': ?0 }")
    List<ParkingAllocation> findByParkingSubSpaceId(String subSpaceId);



    List<ParkingAllocation> findAll();
    @Query("{ 'violationStatus' : { $in: ['UNPAID', 'UNDER_REVIEW'] }, 'status' : 'EXPIRED' }")
    List<ParkingAllocation> findViolationParking();

    long countByStatus(ParkingAllocationStatus status);
    List<ParkingAllocation> findAllByStartTimeBetweenAndEndTimeBetween(LocalDateTime startStartTime, LocalDateTime startEndTime,
                                                                       LocalDateTime endStartTime, LocalDateTime endEndTime);
   // List<ParkingAllocation> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
  //  List<ParkingAllocation> findByEndTimeBetween(LocalDateTime start, LocalDateTime end);
   // ParkingLot findByParkingsubSpace_ParkingSpaceref_ParkingLotId_Parkinglotid(String parkingLotId);
    @Aggregation(pipeline = {
            "{ $match: { 'parkingsubSpace.parkingSpaceref.parkingLotId': ?0, 'startTime': { $gte: ?1 }, 'endTime': { $lte: ?2 } } }"
    })

    List<ParkingAllocation> findAllocationsByParkingLotIdAndTimeInterval(String parkingLotId, LocalDateTime startTime, LocalDateTime endTime);
    @Aggregation(pipeline = {
            "{ $match: { 'parkingsubSpace.parkingSpaceref.parkingLotId': ?0 } }"
    })

    List<ParkingAllocation> findAllocationsByParkingLotId(String parkingLotId);

}
