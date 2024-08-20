package com.tawasalna.facilitiesmanagement.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "ParkingSubSpace")
public class ParkingSubSpace {
    @Id
    private String  subSpaceId;
    @DBRef
    private ParkingSpace parkingSpaceref;
    private String     stationNumber;
    private ParkingSubSpaceStatus status ;


}
