package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSubSpaceRepository extends MongoRepository<ParkingSubSpace, String> {



   List<ParkingSubSpace> findByParkingSpaceref(String parkingSpaceId);


}

