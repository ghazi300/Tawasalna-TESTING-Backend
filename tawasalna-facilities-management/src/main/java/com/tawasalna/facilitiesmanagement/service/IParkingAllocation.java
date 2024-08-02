package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IParkingAllocation {

     ParkingAllocation add(ParkingAllocation parkingAllocation);
     List<ParkingAllocation> getParkingAllocations();


     List<ParkingSubSpace> getSubSpacesByParkingAllocation(String subSpaceId);
      ParkingAllocation updateEndTime(ParkingAllocation allocation);
    void deleteAllocation(String id);

    ParkingAllocation addfine(ParkingAllocation allocation);
    List<ParkingAllocation> getViolationParking();

    Long calculateTotalActiveVehicles();
    Map<String, Object> calculateAdvancedTrafficStatistics(LocalDateTime startTime, LocalDateTime endTime);
}
