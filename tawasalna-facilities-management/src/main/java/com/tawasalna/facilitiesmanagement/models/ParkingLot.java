package com.tawasalna.facilitiesmanagement.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private int totalspace;


}
