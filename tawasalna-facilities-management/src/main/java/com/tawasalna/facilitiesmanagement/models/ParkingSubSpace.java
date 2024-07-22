package com.tawasalna.facilitiesmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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
    @DocumentReference
    @JsonIgnore
    private ParkingSpace parkingSpaceId;
    private String     stationNumber;
    private ParkingSubSpaceStatus status ;


}
