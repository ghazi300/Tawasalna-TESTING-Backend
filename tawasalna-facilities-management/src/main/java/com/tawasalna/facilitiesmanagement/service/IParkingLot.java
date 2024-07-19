package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;

import java.util.List;

public interface IParkingLot {
    List<ParkingLot> getParkingLot();

    ParkingLot add(ParkingLot parkingLot);
    ParkingLot update(String id, ParkingLot parkingLot);
    void delete(String id);



}
