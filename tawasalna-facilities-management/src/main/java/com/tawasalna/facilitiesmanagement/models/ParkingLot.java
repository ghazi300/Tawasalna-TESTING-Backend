package com.tawasalna.facilitiesmanagement.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "ParkingLot")
public class ParkingLot {
    @Id
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
