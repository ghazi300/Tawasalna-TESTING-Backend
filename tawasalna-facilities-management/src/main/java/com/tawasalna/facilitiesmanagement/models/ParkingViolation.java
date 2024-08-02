package com.tawasalna.facilitiesmanagement.models;

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
@Document(collection = "ParkingViolation")
public class ParkingViolation {
    @Id
  private String  violationId;
    @DocumentReference
    private ParkingAllocation   allocationId;
    private String    violationType;
    private float fineAmount;
    private ParkingViolationStatus  status;
    private LocalDateTime issueDate;
   private  LocalDateTime paymentDate;


}
