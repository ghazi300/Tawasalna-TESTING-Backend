package com.tawasalna.facilitiesmanagement.dto;

import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpaceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSubSpacedto {
    private String  subSpaceId;
    private ParkingSpace parkingSpaceref;
    private String     stationNumber;
    private ParkingSubSpaceStatus status ;
}
