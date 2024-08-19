package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.*;
import com.tawasalna.facilitiesmanagement.repository.ParkingAllocationRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSubSpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)

class ParkingAllocationImplIT {
    @Mock
    private ParkingAllocationRepository parkingAllocationRepository;

    @Mock
    private ParkingSubSpaceRepository parkingSubSpaceRepository;

    @InjectMocks
    private ParkingAllocationImpl parkingAllocationService;
    private ParkingAllocation allocation;
    private ParkingSubSpace subSpace;
    @BeforeEach
    void setUp() {
        subSpace = new ParkingSubSpace();
        subSpace.setSubSpaceId("subspace1");
        subSpace.setStatus(ParkingSubSpaceStatus.AVAILABLE);

        allocation = new ParkingAllocation();
        allocation.setAllocationId("alloc1");
        allocation.setParkingsubSpace(subSpace);
        allocation.setStartTime(LocalDateTime.now().minusHours(2));
        allocation.setEndTime(LocalDateTime.now().plusHours(1));
        allocation.setStatus(ParkingAllocationStatus.ACTIVE);
        allocation.setViolationStatus(ParkingViolationStatus.UNPAID);
    }

    @Test
    void add() {
        when(parkingSubSpaceRepository.findById(anyString())).thenReturn(Optional.of(subSpace));
        when(parkingAllocationRepository.save(any(ParkingAllocation.class))).thenReturn(allocation);

        ParkingAllocation result = parkingAllocationService.add(allocation);

        assertNotNull(result);
        assertEquals(ParkingSubSpaceStatus.OCCUPIED, result.getParkingsubSpace().getStatus());
        verify(parkingSubSpaceRepository, times(1)).save(subSpace);
        verify(parkingAllocationRepository, times(1)).save(allocation);
    }

    @Test
    void getParkingAllocations() {
        ParkingSubSpace subSpace = new ParkingSubSpace();
        subSpace.setSubSpaceId("subspace1");
        subSpace.setStatus(ParkingSubSpaceStatus.OCCUPIED);


        ParkingAllocation expiredAllocation1 = new ParkingAllocation();
        expiredAllocation1.setAllocationId("alloc1");
        expiredAllocation1.setParkingsubSpace(subSpace);
        expiredAllocation1.setStartTime(LocalDateTime.now().minusHours(3));
        expiredAllocation1.setEndTime(LocalDateTime.now().minusHours(1));
        expiredAllocation1.setStatus(ParkingAllocationStatus.ACTIVE);


        ParkingAllocation expiredAllocation2 = new ParkingAllocation();
        expiredAllocation2.setAllocationId("alloc2");
        expiredAllocation2.setParkingsubSpace(subSpace);
        expiredAllocation2.setStartTime(LocalDateTime.now().minusHours(4));
        expiredAllocation2.setEndTime(LocalDateTime.now().minusHours(2));
        expiredAllocation2.setStatus(ParkingAllocationStatus.ACTIVE);

        List<ParkingAllocation> allocations = Arrays.asList(expiredAllocation1, expiredAllocation2);

        when(parkingAllocationRepository.findAll()).thenReturn(allocations);
        when(parkingSubSpaceRepository.findById(anyString())).thenReturn(Optional.of(subSpace));

        List<ParkingAllocation> result = parkingAllocationService.getParkingAllocations();

        assertEquals(2, result.size());
        assertEquals(ParkingAllocationStatus.EXPIRED, expiredAllocation1.getStatus());
        assertEquals(ParkingSubSpaceStatus.AVAILABLE, expiredAllocation1.getParkingsubSpace().getStatus());
        assertEquals(ParkingAllocationStatus.EXPIRED, expiredAllocation2.getStatus());
        assertEquals(ParkingSubSpaceStatus.AVAILABLE, expiredAllocation2.getParkingsubSpace().getStatus());

        verify(parkingAllocationRepository, times(1)).findAll();
        verify(parkingSubSpaceRepository, times(2)).save(any(ParkingSubSpace.class));
        verify(parkingAllocationRepository, times(2)).save(any(ParkingAllocation.class));
    }


    @Test
    void updateEndTime() {
        when(parkingSubSpaceRepository.findById(anyString())).thenReturn(Optional.of(subSpace));
        when(parkingAllocationRepository.save(any(ParkingAllocation.class))).thenReturn(allocation);

        ParkingAllocation result = parkingAllocationService.updateEndTime(allocation);

        assertNotNull(result);
        assertEquals(ParkingSubSpaceStatus.OCCUPIED, result.getParkingsubSpace().getStatus());
        verify(parkingSubSpaceRepository, times(1)).save(subSpace);
        verify(parkingAllocationRepository, times(1)).save(allocation);
    }

    @Test
    void deleteAllocation() {
        when(parkingAllocationRepository.findById(anyString())).thenReturn(Optional.of(allocation));
        when(parkingSubSpaceRepository.findById(anyString())).thenReturn(Optional.of(subSpace));

        parkingAllocationService.deleteAllocation(allocation.getAllocationId());

        verify(parkingSubSpaceRepository, times(1)).save(subSpace);
        verify(parkingAllocationRepository, times(1)).deleteById(allocation.getAllocationId());

    }

    @Test
    void addfine() {
        allocation.setStatus(ParkingAllocationStatus.EXPIRED);
        allocation.setEndTime(LocalDateTime.now().minusHours(2));

        when(parkingAllocationRepository.findById(anyString())).thenReturn(Optional.of(allocation));
        when(parkingAllocationRepository.save(any(ParkingAllocation.class))).thenReturn(allocation);

        ParkingAllocation fineAllocation = new ParkingAllocation();
        fineAllocation.setAllocationId(allocation.getAllocationId());
        fineAllocation.setViolationType("Speeding");
        fineAllocation.setFineAmount(100);
        fineAllocation.setIssueDate(LocalDateTime.now());
        fineAllocation.setPaymentDate(LocalDateTime.now().plusDays(10));

        ParkingAllocation result = parkingAllocationService.addfine(fineAllocation);

        assertNotNull(result);
        assertEquals("Speeding", result.getViolationType());
        assertEquals(100, result.getFineAmount());

        verify(parkingAllocationRepository, times(1)).save(allocation); }


    @Test
    void calculateTotalActiveVehicles() {
        when(parkingAllocationRepository.countByStatus(ParkingAllocationStatus.ACTIVE)).thenReturn(5L);

        Long activeVehicles = parkingAllocationService.calculateTotalActiveVehicles();

        assertEquals(5L, activeVehicles);
        verify(parkingAllocationRepository, times(1)).countByStatus(ParkingAllocationStatus.ACTIVE);

    }

   /* @Test
    void calculateAdvancedTrafficStatistics() {
        List<ParkingAllocation> allocations = Arrays.asList(allocation);

        when(parkingAllocationRepository.findAllByStartTimeBetweenAndEndTimeBetween(any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(allocations);

        Map<String, Object> statistics = parkingAllocationService.calculateAdvancedTrafficStatistics(LocalDateTime.now().minusDays(1), LocalDateTime.now());

        assertNotNull(statistics);
        assertEquals(1L, statistics.get("totalVehicles"));
        assertTrue(statistics.containsKey("entriesPerHour"));
        assertTrue(statistics.containsKey("exitsPerHour"));
        assertTrue(statistics.containsKey("averageParkingDurationMinutes"));
    }*/
}