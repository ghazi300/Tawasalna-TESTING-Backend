package com.tawasalna.facilitiesmanagement.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "ParkingAllocation")
public class ParkingAllocation {
    @Id
    private String allocationId;

    @DocumentReference
    private ParkingSubSpace parkingsubSpace;

    private String vehiculecategory;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private ParkingAllocationStatus status;
    private String vehicleNumber;



}
