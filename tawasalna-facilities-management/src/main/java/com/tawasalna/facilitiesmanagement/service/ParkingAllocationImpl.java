package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.*;
import com.tawasalna.facilitiesmanagement.repository.ParkingAllocationRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSubSpaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingAllocationImpl implements IParkingAllocation{
    private final ParkingAllocationRepository parkingAllocationRepository;
    private final ParkingSubSpaceRepository parkingSubSpaceRepository;

    //add
    @Override
    public ParkingAllocation add(ParkingAllocation parkingAllocation) {

        ParkingSubSpace parkingSubSpace = parkingSubSpaceRepository.findById(parkingAllocation.getParkingsubSpace().getSubSpaceId() ).orElseThrow(() -> new IllegalArgumentException("ParkingSubSpace with id   not found"));
        parkingSubSpace.setStatus(ParkingSubSpaceStatus.OCCUPIED);

        parkingSubSpaceRepository.save(parkingSubSpace);

        parkingAllocation.setParkingsubSpace(parkingSubSpace);

        return parkingAllocationRepository.save(parkingAllocation);
    }

    @Override
    public List<ParkingAllocation> getParkingAllocations() {
        try {
            List<ParkingAllocation> allocations = parkingAllocationRepository.findAll();
            LocalDateTime now = LocalDateTime.now();

            for (ParkingAllocation allocation : allocations) {
                if (allocation.getEndTime().isBefore(now)) {
                    try {
                        ParkingSubSpace parkingSubSpace = parkingSubSpaceRepository.findById(allocation.getParkingsubSpace().getSubSpaceId())
                                .orElseThrow(() -> new IllegalArgumentException("ParkingSubSpace with id not found"));
                        parkingSubSpace.setStatus(ParkingSubSpaceStatus.AVAILABLE);
                        parkingSubSpaceRepository.save(parkingSubSpace);

                        allocation.setStatus(ParkingAllocationStatus.EXPIRED);
                        parkingAllocationRepository.save(allocation);
                    } catch (Exception e) {
                        log.error("Error while updating ParkingSubSpace or ParkingAllocation: ", e);
                    }
                }
            }

            return allocations;
        } catch (Exception e) {
            log.error("Error while retrieving ParkingAllocations: ", e);
            throw new RuntimeException("Failed to retrieve ParkingAllocations");
        }
    }

    public List<ParkingSubSpace> getSubSpacesByParkingAllocation(String subSpaceId) {
        List<ParkingAllocation> allocations = parkingAllocationRepository.findByParkingSubSpaceId(subSpaceId);

        return allocations.stream()
                .map(ParkingAllocation::getParkingsubSpace)
                .distinct()
                .collect(Collectors.toList());
    }


  public ParkingAllocation updateEndTime(ParkingAllocation allocation) {
      try {
          if (allocation == null) {
              throw new IllegalArgumentException("ParkingAllocation object cannot be null");
          }

          LocalDateTime endTime = allocation.getEndTime();
          if (endTime == null) {
              throw new IllegalArgumentException("EndTime cannot be null");
          }

          LocalDateTime now = LocalDateTime.now();

          ParkingSubSpace parkingSubSpace = parkingSubSpaceRepository.findById(allocation.getParkingsubSpace().getSubSpaceId())
                  .orElseThrow(() -> new IllegalArgumentException("ParkingSubSpace with id  not found"));



          if (endTime.isAfter(now)) {
              parkingSubSpace.setStatus(ParkingSubSpaceStatus.OCCUPIED);
              allocation.setStatus(ParkingAllocationStatus.ACTIVE);
          } else {

              parkingSubSpace.setStatus(ParkingSubSpaceStatus.AVAILABLE);
              allocation.setStatus(ParkingAllocationStatus.EXPIRED);
          }


          parkingSubSpaceRepository.save(parkingSubSpace);


          ParkingAllocation updatedAllocation = parkingAllocationRepository.save(allocation);


          log.info("Updated ParkingAllocation with id {}: EndTime set to {}", updatedAllocation.getAllocationId(), endTime);

          return updatedAllocation;
      } catch (Exception e) {

          log.error("Error while updating end time for ParkingAllocation with id {}: ", allocation.getAllocationId(), e);

          throw new RuntimeException("Failed to update end time");
      }
  }




    public void deleteAllocation(String allocationId) {
        try {

            ParkingAllocation allocation = parkingAllocationRepository.findById(allocationId)
                    .orElseThrow(() -> new IllegalArgumentException("ParkingAllocation with id " + allocationId + " not found"));

            ParkingSubSpace parkingSubSpace = allocation.getParkingsubSpace();
            if (parkingSubSpace != null) {
                parkingSubSpace = parkingSubSpaceRepository.findById(parkingSubSpace.getSubSpaceId())
                        .orElseThrow(() -> new IllegalArgumentException("ParkingSubSpace with id " + allocation.getParkingsubSpace().getSubSpaceId() + " not found"));


                parkingSubSpace.setStatus(ParkingSubSpaceStatus.AVAILABLE);
                parkingSubSpaceRepository.save(parkingSubSpace);
            } else {
                throw new IllegalArgumentException("Associated ParkingSubSpace is null");
            }


            parkingAllocationRepository.deleteById(allocationId);

            log.info("Deleted ParkingAllocation with id {} and updated associated ParkingSubSpace status to AVAILABLE", allocationId);

        } catch (Exception e) {
            log.error("Error while deleting ParkingAllocation with id {}: ", allocationId, e);
            throw new RuntimeException("Failed to delete allocation", e);
        }
    }

    @Override
    public ParkingAllocation addfine(ParkingAllocation allocation) {
        Optional<ParkingAllocation> allocationOptional = parkingAllocationRepository.findById(allocation.getAllocationId());

        if (allocationOptional.isPresent()) {
            ParkingAllocation existingAllocation = allocationOptional.get();

            if ((existingAllocation.getViolationStatus() == ParkingViolationStatus.UNPAID
                    || existingAllocation.getViolationStatus() == ParkingViolationStatus.UNDER_REVIEW)
                    && existingAllocation.getStatus() == ParkingAllocationStatus.EXPIRED) {

                existingAllocation.setViolationType(allocation.getViolationType());
                existingAllocation.setFineAmount(allocation.getFineAmount());
                existingAllocation.setIssueDate(allocation.getIssueDate());
                existingAllocation.setPaymentDate(allocation.getPaymentDate());

                return parkingAllocationRepository.save(existingAllocation);
            } else {
                throw new IllegalStateException("The parking allocation does not meet the criteria for adding a fine.");
            }
        } else {
            throw new IllegalArgumentException("Parking allocation with ID " + allocation.getAllocationId() + " does not exist.");
        }
    }

    @Override
    public List<ParkingAllocation> getViolationParking() {
     return     parkingAllocationRepository.findViolationParking();
    }
    @Override
    public Long calculateTotalActiveVehicles() {
        return  parkingAllocationRepository.countByStatus(ParkingAllocationStatus.ACTIVE);

    }

    @Override
    public boolean verify(ParkingLot parkingLot) {

       List<ParkingAllocation> parkingSubSpaces=   parkingAllocationRepository.findAllByParkingsubSpace();

             System.out.print(parkingSubSpaces);

        return false;
    }
}
