package com.tawasalna.facilitiesmanagement.dto;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocationStatus;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingViolationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAllocationdto {
    private String allocationId;
    private ParkingSubSpace parkingsubSpace;
    private String vehiculecategory;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private ParkingAllocationStatus status;
    private String vehicleNumber;
    private String violationType;
    private float fineAmount;
    private ParkingViolationStatus violationStatus;
    private LocalDateTime issueDate;
    private LocalDateTime paymentDate;


}
