package com.tawasalna.facilitiesmanagement.dto;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpacedto {


    private String  parkingSpaceId;
    private ParkingLot parkingLotId;
    private String    locationNumber;
    private int capacity;
    private int   occupiedSpaces;
    private List<ParkingSubSpace> subSpaces;
}
