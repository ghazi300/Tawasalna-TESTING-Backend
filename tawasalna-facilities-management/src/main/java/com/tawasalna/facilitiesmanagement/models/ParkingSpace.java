package com.tawasalna.facilitiesmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "ParkingSpace")
public class ParkingSpace {
       @Id
       private String  parkingSpaceId;
      @DocumentReference
      private ParkingLot  parkingLotId;
    private String    locationNumber;
    private int capacity;
    private int   occupiedSpaces;
    @DBRef(lazy = false)
    private List<ParkingSubSpace> subSpaces;
}
