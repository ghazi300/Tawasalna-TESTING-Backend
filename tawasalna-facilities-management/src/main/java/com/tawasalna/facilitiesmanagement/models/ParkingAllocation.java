package com.tawasalna.facilitiesmanagement.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tawasalna.facilitiesmanagement.configuration.CustomLocalDateTimeDeserializer;
import lombok.*;
import org.springframework.data.annotation.Id;
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
   // @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
   @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)

    private LocalDateTime endTime;
  //  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)

    private LocalDateTime startTime;
    private ParkingAllocationStatus status;
    private String vehicleNumber;
    private String violationType;
    private float fineAmount;
    private ParkingViolationStatus violationStatus;
    private LocalDateTime issueDate;
    private LocalDateTime paymentDate;



}
