package com.tawasalna.facilitiesmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotdto {


    private String parkinglotid;
    private String name;
    private String loacation;
    private double latitude;
    private double longitude;
    private int totalspace;
    private List<String> route;

    private LocalDateTime openingdate;
    private LocalDateTime closingdate;

}
