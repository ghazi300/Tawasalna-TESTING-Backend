package com.tawasalna.facilitiesmanagement.repository;

import com.tawasalna.facilitiesmanagement.models.ParkingViolation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingViolationRepository extends MongoRepository<ParkingViolation, String> {
    boolean existsByAllocationId_AllocationId(String allocationId);

}
