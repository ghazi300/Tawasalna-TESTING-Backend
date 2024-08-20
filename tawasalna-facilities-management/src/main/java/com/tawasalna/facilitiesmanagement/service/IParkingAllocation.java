package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;

import java.util.List;

public interface IParkingAllocation {

     ParkingAllocation add(ParkingAllocation parkingAllocation);
     List<ParkingAllocation> getParkingAllocations();


     List<ParkingSubSpace> getSubSpacesByParkingAllocation(String subSpaceId);
      ParkingAllocation updateEndTime(ParkingAllocation allocation);
    void deleteAllocation(String id);

    ParkingAllocation addfine(ParkingAllocation allocation);
    List<ParkingAllocation> getViolationParking();

    Long calculateTotalActiveVehicles();
     boolean verify(ParkingLot parkingLot);
}
