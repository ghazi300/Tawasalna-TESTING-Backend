package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;

import java.util.List;

public interface IParkingAllocation {

     ParkingAllocation add(ParkingAllocation parkingAllocation);
     List<ParkingAllocation> getParkingAllocations();


     List<ParkingSubSpace> getSubSpacesByParkingAllocation(String subSpaceId);

}
