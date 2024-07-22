package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;

import java.util.List;

public interface IParkingSpace {
      List<ParkingSpace> getParkingSpaces();
      ParkingSpace addParkingSpace(ParkingSpace parkingSpace);
      ParkingSpace update(String id, ParkingSpace parkingSpace);
      void delete(String id);
      List<ParkingSubSpace> getParkingSubSpaces();

}
