package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.*;
import com.tawasalna.facilitiesmanagement.repository.ParkingAllocationRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingLotRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSubSpaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingAllocationImpl implements IParkingAllocation{
    private final ParkingAllocationRepository parkingAllocationRepository;
    private final ParkingSubSpaceRepository parkingSubSpaceRepository;
    private final ParkingLotRepository parkingLotRepository;

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

   /* public Map<String, Object> calculateAdvancedTrafficStatistics(LocalDateTime startTime, LocalDateTime endTime) {
        List<ParkingAllocation> allocations = parkingAllocationRepository.findAllByStartTimeBetweenAndEndTimeBetween(startTime, endTime, startTime, endTime);
        Map<String, Long> entriesPerHour = allocations.stream()
                .collect(Collectors.groupingBy(allocation -> String.valueOf(allocation.getStartTime().getHour()), Collectors.counting()));

        Map<String, Long> exitsPerHour = allocations.stream()
                .collect(Collectors.groupingBy(allocation -> String.valueOf(allocation.getEndTime().getHour()), Collectors.counting()));

        double averageParkingDuration = allocations.stream()
                .mapToLong(allocation -> Duration.between(allocation.getStartTime(), allocation.getEndTime()).toMinutes())
                .average()
                .orElse(0);

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalVehicles", (long) allocations.size());
        statistics.put("entriesPerHour", entriesPerHour);
        statistics.put("exitsPerHour", exitsPerHour);
        statistics.put("averageParkingDurationMinutes", averageParkingDuration);

        return statistics;
    }
*/
 /* @Override
    public Map<LocalDateTime, Integer> analyzeEntries(String parkingLotId) {
        Map<LocalDateTime, Integer> entriesMap = new HashMap<>();

        try {

            ParkingLot parkingLot = parkingAllocationRepository.findByParkingsubSpace_ParkingSpaceref_ParkingLotId_Parkinglotid(parkingLotId);
            System.err.println("parkingLot ************ : "+parkingLot);

            if (parkingLot != null) {
                LocalDateTime start = parkingLot.getOpeningdate();
                LocalDateTime end = parkingLot.getClosingdate();
                System.err.println("start ************ : "+start);

                while (start.isBefore(end)) {
                    List<ParkingAllocation> allocations = parkingAllocationRepository.findByStartTimeBetween(start, start.plus(15, ChronoUnit.MINUTES));

                    System.err.println("allocations ************ : "+allocations);

                    entriesMap.put(start, allocations.size());
                    start = start.plus(15, ChronoUnit.MINUTES);
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while analyzing entries: " + e.getMessage());
        }

        return entriesMap;
    }*/






   /* public Map<LocalDateTime, Integer> analyzeExits(String parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

        LocalDateTime start = parkingLot.getOpeningdate();
        LocalDateTime end = parkingLot.getClosingdate();

        Map<LocalDateTime, Integer> exitsMap = new HashMap<>();
        while (start.isBefore(end)) {
            List<ParkingAllocation> allocations = parkingAllocationRepository.findByEndTimeBetween(start, start.plus(15, ChronoUnit.MINUTES));
            exitsMap.put(start, allocations.size());
            start = start.plus(15, ChronoUnit.MINUTES);
        }

        return exitsMap;
    }*/


   /* public long countAllocationsByParkingLotId(String parkingLotId) {
        System.out.print(parkingLotId);
        // Récupérer les informations du ParkingLot
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new RuntimeException("ParkingLot not found"));

        // Obtenir les heures d'ouverture et de fermeture
        LocalDateTime openingDate = parkingLot.getOpeningdate();
        LocalDateTime closingDate = parkingLot.getClosingdate();

        // Trouver les allocations dans l'intervalle de temps
        List<ParkingAllocation> allocations = parkingAllocationRepository.findAllocationsByParkingLotIdAndTimeInterval(
                parkingLotId, openingDate, closingDate
        );

        return allocations.size();
    }*/
   @Override
    public long countVehiclesEnteredDuringRange(String parkingLotId) {
        // Step 1: Retrieve ParkingLot by ID and verify its existence
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findById(parkingLotId);
        if (optionalParkingLot.isEmpty()) {
            throw new IllegalArgumentException("Parking lot with ID " + parkingLotId + " does not exist.");
        }
        ParkingLot parkingLot = optionalParkingLot.get();
        // Step 2: Verify if any ParkingAllocation is associated with this ParkingLot ID
        List<ParkingAllocation> allocations = parkingAllocationRepository.findAllocationsByParkingLotId(
                parkingLotId);
          System.out.print("allocations:***********"+allocations);
        // Step 3: Get the openingdate and closingdate of the ParkingLot
        LocalTime openingTime = parkingLot.getOpeningdate().toLocalTime();
        LocalTime closingTime = parkingLot.getClosingdate().toLocalTime();
        // Step 4: Get list of allocations at this parking lot and check if the status is ACTIVE
       return allocations.stream()
                .filter(allocation -> allocation.getStatus() == ParkingAllocationStatus.ACTIVE)
                .filter(allocation -> {
                    LocalTime startTime = allocation.getStartTime().toLocalTime();
                    return !startTime.isBefore(openingTime) && !startTime.isAfter(closingTime);
                })
                .count();
    }
}
