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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ParkingSpaceImplUT {
    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingSubSpaceRepository parkingSubSpaceRepository;

    @InjectMocks
    private ParkingSpaceImpl parkingSpaceService;

    private ParkingSpace parkingSpace;
    private ParkingLot parkingLot;
    private ParkingSubSpace parkingSubSpace;
    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot("1", "Lot A", "Location A", 12.345, 67.890, 100, Arrays.asList("Route1", "Route2"), null, null);
        parkingSpace = new ParkingSpace("1", parkingLot, "LN-123", 50, 10, null);
        parkingSubSpace = new ParkingSubSpace("1", parkingSpace, "Station-1", ParkingSubSpaceStatus.AVAILABLE);

    }

    @Test
    void getParkingSpaces() {
        when(parkingSpaceRepository.findAll()).thenReturn(Arrays.asList(parkingSpace));

        List<ParkingSpace> result = parkingSpaceService.getParkingSpaces();

        assertEquals(1, result.size());
        assertEquals(parkingSpace, result.get(0));
    }

    @Test
    void getParkingSubSpaces() {
        when(parkingLotRepository.findById(parkingLot.getParkinglotid())).thenReturn(Optional.of(parkingLot));
        when(parkingSpaceRepository.save(parkingSpace)).thenReturn(parkingSpace);

        ParkingSpace result = parkingSpaceService.addParkingSpace(parkingSpace);

        assertEquals(parkingSpace, result);
        verify(parkingSpaceRepository, times(1)).save(parkingSpace);
        verify(parkingSubSpaceRepository, times(parkingSpace.getCapacity())).save(any(ParkingSubSpace.class));

    }

    @Test
    void addParkingSpace() {
        when(parkingLotRepository.findById(parkingLot.getParkinglotid())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingSpaceService.addParkingSpace(parkingSpace);
        });

        assertEquals("ParkingLot with id  not found", exception.getMessage());
        verify(parkingSpaceRepository, times(0)).save(any(ParkingSpace.class));

    }


   @Test
   void update() {
       int oldCapacity = 10;
       int newCapacity = 15;

       ParkingSpace existingParkingSpace = new ParkingSpace("1", parkingLot, "LN-123", oldCapacity, 5, null);
       ParkingSpace updatedParkingSpace = new ParkingSpace("1", parkingLot, "LN-456", newCapacity, 5, null);

       List<ParkingSubSpace> existingSubSpaces = IntStream.range(1, oldCapacity + 1)
               .mapToObj(i -> new ParkingSubSpace("sub-" + i, existingParkingSpace, "Station-" + i, ParkingSubSpaceStatus.AVAILABLE))
               .collect(Collectors.toList());

       when(parkingSpaceRepository.findById(existingParkingSpace.getParkingSpaceId())).thenReturn(Optional.of(existingParkingSpace));
       when(parkingSubSpaceRepository.findByParkingSpaceref(existingParkingSpace.getParkingSpaceId())).thenReturn(existingSubSpaces);
       when(parkingSpaceRepository.save(any(ParkingSpace.class))).thenReturn(updatedParkingSpace);


       ParkingSpace result = parkingSpaceService.update(existingParkingSpace.getParkingSpaceId(), updatedParkingSpace);


       assertEquals(updatedParkingSpace.getLocationNumber(), result.getLocationNumber());
       assertEquals(updatedParkingSpace.getCapacity(), result.getCapacity());


       verify(parkingSubSpaceRepository, times(newCapacity)).save(any(ParkingSubSpace.class));


       verify(parkingSpaceRepository, times(1)).save(existingParkingSpace);}

    @Test
    void deletespace() {
        when(parkingSpaceRepository.findById(parkingSpace.getParkingSpaceId())).thenReturn(Optional.of(parkingSpace));
        when(parkingSubSpaceRepository.findByParkingSpaceref(parkingSpace.getParkingSpaceId()))
                .thenReturn(Arrays.asList(parkingSubSpace));

        parkingSpaceService.deletespace(parkingSpace.getParkingSpaceId());

        verify(parkingSubSpaceRepository, times(1)).delete(parkingSubSpace);
        verify(parkingSpaceRepository, times(1)).delete(parkingSpace);

    }
}