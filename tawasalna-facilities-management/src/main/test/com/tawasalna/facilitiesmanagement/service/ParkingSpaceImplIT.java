package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpaceStatus;
import com.tawasalna.facilitiesmanagement.repository.ParkingLotRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSpaceRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSubSpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ParkingSpaceImplIT {


    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingSubSpaceRepository parkingSubSpaceRepository;
    @InjectMocks
    private ParkingSpaceImpl parkingSpaceService;

    private ParkingLot parkingLot;
    private ParkingSpace parkingSpace;
    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot("1", "Lot A", "Location A", 12.345, 67.890, 100, Arrays.asList("Route1", "Route2"), null, null);
        parkingSpace = new ParkingSpace("2", parkingLot, "LN-123", 50, 10, null);

        // Mock the save method
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);
        when(parkingSpaceRepository.save(any(ParkingSpace.class))).thenReturn(parkingSpace);

        // Mock the findById methods to return the correct objects
        when(parkingLotRepository.findById("1")).thenReturn(Optional.of(parkingLot));
        when(parkingSpaceRepository.findById("2")).thenReturn(Optional.of(parkingSpace));
    }

    @Test
    void getParkingSpaces() {
        when(parkingSpaceRepository.findAll()).thenReturn(List.of(parkingSpace));

        List<ParkingSpace> result = parkingSpaceService.getParkingSpaces();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(parkingSpace, result.get(0));
    }



    @Test
    void addParkingSpace() {
        ParkingSpace newParkingSpace = new ParkingSpace("3", parkingLot, "LN-456", 60, 15, null);

        when(parkingSpaceRepository.save(any(ParkingSpace.class))).thenReturn(newParkingSpace);

        ParkingSpace result = parkingSpaceService.addParkingSpace(newParkingSpace);

        assertNotNull(result);
        assertEquals("LN-456", result.getLocationNumber());
        assertEquals(60, result.getCapacity());
    }

    @Test
    void update() {
        int oldCapacity = 10;
        int newCapacity = 15;

        ParkingSpace updatedParkingSpace = new ParkingSpace("2", parkingLot, "LN-123", newCapacity, 5, null);

        List<ParkingSubSpace> existingSubSpaces = IntStream.range(1, oldCapacity + 1)
                .mapToObj(i -> new ParkingSubSpace("sub-" + i, parkingSpace, "Station-" + i, ParkingSubSpaceStatus.AVAILABLE))
                .collect(Collectors.toList());

        when(parkingSubSpaceRepository.saveAll(existingSubSpaces)).thenReturn(existingSubSpaces);
        when(parkingSubSpaceRepository.count()).thenReturn((long) newCapacity);

        when(parkingSpaceRepository.findById("2")).thenReturn(Optional.of(parkingSpace));
        when(parkingSpaceRepository.save(any(ParkingSpace.class))).thenReturn(updatedParkingSpace);

        ParkingSpace result = parkingSpaceService.update("2", updatedParkingSpace);

        assertNotNull(result);
        assertEquals("LN-123", result.getLocationNumber());
        assertEquals(newCapacity, result.getCapacity());
        assertEquals(newCapacity, parkingSubSpaceRepository.count());
    }

    @Test
    void deletespace() {
        parkingSpaceService.deletespace("1");

        assertFalse(parkingSpaceRepository.findById("1").isPresent());
        assertTrue(parkingSubSpaceRepository.findByParkingSpaceref("1").isEmpty());
    }
}