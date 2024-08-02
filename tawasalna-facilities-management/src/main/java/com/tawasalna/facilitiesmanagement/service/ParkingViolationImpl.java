package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.*;
import com.tawasalna.facilitiesmanagement.repository.ParkingAllocationRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingViolationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingViolationImpl  implements IParkingViolation{
    private final ParkingAllocationRepository parkingAllocationRepository;
    private final ParkingViolationRepository parkingViolationRepository;

    public List<ParkingViolation> generateParkingViolations() {
        List<ParkingViolation> violations = new ArrayList<>();

        // Fetch all parking allocations
        List<ParkingAllocation> allocations = parkingAllocationRepository.findAll();

        // Filter expired allocations and create violations
        for (ParkingAllocation allocation : allocations) {
            if (allocation.getStatus() == ParkingAllocationStatus.EXPIRED) {
                // Check if a violation already exists for this allocation
                boolean violationExists = parkingViolationRepository.existsByAllocationId_AllocationId(allocation.getAllocationId());
    System.out.print(violationExists);
                if (!violationExists) {
                    ParkingViolation violation = new ParkingViolation();
                    violation.setAllocationId(allocation);
                    violation.setViolationType("Expired Allocation");
                    violation.setFineAmount(50.0f);
                    violation.setStatus(ParkingViolationStatus.UNPAID);
                    violation.setIssueDate(LocalDateTime.now());
                    violation.setPaymentDate(null);
                    violations.add(violation);
                }
            }
        }

        // Save only unique violations to the database
        parkingViolationRepository.saveAll(violations);

        return parkingViolationRepository.findAll();
    }


  /*  private float calculateFineAmount(ParkingAllocation allocation) {
        return 50.0f;
    }*/
}
