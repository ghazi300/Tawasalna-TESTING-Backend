package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.*;
import com.tawasalna.facilitiesmanagement.repository.ParkingAllocationRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSpaceRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSubSpaceRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingViolationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingAllocationImpl implements IParkingAllocation{
    private final ParkingAllocationRepository parkingAllocationRepository;
    private final ParkingSubSpaceRepository parkingSubSpaceRepository;

    @Override
    public ParkingAllocation add(ParkingAllocation parkingAllocation) {

        ParkingSubSpace parkingSubSpace = parkingSubSpaceRepository.findById(parkingAllocation.getParkingsubSpace().getSubSpaceId() ).orElseThrow(() -> new IllegalArgumentException("ParkingSubSpace with id " + parkingAllocation.getParkingsubSpace().getSubSpaceId() + " not found"));
        parkingSubSpace.setStatus(ParkingSubSpaceStatus.OCCUPIED);

        parkingSubSpaceRepository.save(parkingSubSpace);

        parkingAllocation.setParkingsubSpace(parkingSubSpace);

        return parkingAllocationRepository.save(parkingAllocation);
    }

    @Override
    public List<ParkingAllocation> getParkingAllocations() {
        return parkingAllocationRepository.findAll();
    }

    public List<ParkingSubSpace> getSubSpacesByParkingAllocation(String subSpaceId) {
        List<ParkingAllocation> allocations = parkingAllocationRepository.findByParkingSubSpaceId(subSpaceId);

        return allocations.stream()
                .map(ParkingAllocation::getParkingsubSpace)
                .distinct()
                .collect(Collectors.toList());
    }
}
