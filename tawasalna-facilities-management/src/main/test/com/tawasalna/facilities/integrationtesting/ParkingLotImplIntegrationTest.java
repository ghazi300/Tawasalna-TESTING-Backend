package com.tawasalna.facilities.integrationtesting;

import com.tawasalna.facilitiesmanagement.controller.ParkingLotController;
import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.repository.ParkingLotRepository;
import com.tawasalna.facilitiesmanagement.service.ParkingLotImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest(classes = ParkingLotController.class)

@ExtendWith(MockitoExtension.class)
public class ParkingLotImplIntegrationTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotImpl parkingLotService;

    private ParkingLot mockParkingLot;

    @BeforeEach
    void setUp() {
        mockParkingLot = new ParkingLot("1", "Lot A", "Location A", 12.345, 67.890, 100, Arrays.asList("Route1", "Route2"), null, null);
    }

    @Test
    void getParkingLot_shouldReturnParkingLots() {
        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(mockParkingLot));

        List<ParkingLot> result = parkingLotService.getParkingLot();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockParkingLot, result.get(0));
        verify(parkingLotRepository).findAll();
    }

    @Test
    void addParkingLot_shouldSaveAndReturnParkingLot() {
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(mockParkingLot);

        ParkingLot result = parkingLotService.add(mockParkingLot);

        assertNotNull(result);
        assertEquals(mockParkingLot, result);
        verify(parkingLotRepository).save(mockParkingLot);
    }

    @Test
    void updateParkingLot_shouldUpdateAndReturnUpdatedParkingLot() {
        when(parkingLotRepository.findById("1")).thenReturn(Optional.of(mockParkingLot));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(mockParkingLot);

        ParkingLot updatedParkingLot = new ParkingLot("1", "Lot B", "Location B", 12.345, 67.890, 150, Arrays.asList("Route3"), null, null);
        mockParkingLot.setName(updatedParkingLot.getName());
        mockParkingLot.setLoacation(updatedParkingLot.getLoacation());
        mockParkingLot.setTotalspace(updatedParkingLot.getTotalspace());

        ParkingLot result = parkingLotService.update(mockParkingLot.getParkinglotid(), mockParkingLot);

        assertNotNull(result);
        assertEquals(updatedParkingLot.getName(), result.getName());
        assertEquals(updatedParkingLot.getLoacation(), result.getLoacation());
        assertEquals(updatedParkingLot.getTotalspace(), result.getTotalspace());
        verify(parkingLotRepository).save(mockParkingLot);
    }

    @Test
    void updateParkingLot_shouldThrowExceptionWhenParkingLotNotFound() {
        when(parkingLotRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLotService.update(mockParkingLot.getParkinglotid(), mockParkingLot);
        });

        String expectedMessage = "ParkingLot not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(parkingLotRepository).findById("1");
    }

    @Test
    void deleteParkingLot_shouldDeleteParkingLot() {
        when(parkingLotRepository.findById("1")).thenReturn(Optional.of(mockParkingLot));

        parkingLotService.delete(mockParkingLot.getParkinglotid());

        verify(parkingLotRepository).deleteById("1");
    }

    @Test
    void deleteParkingLot_shouldThrowExceptionWhenParkingLotNotFound() {
        when(parkingLotRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLotService.delete(mockParkingLot.getParkinglotid());
        });

        String expectedMessage = "ParkingLot not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(parkingLotRepository).findById("1");
    }

   /* @Test
    void getDistinctLocationCount_shouldReturnCount() {
        when(parkingLotRepository.findDistinctByLocation()).thenReturn(Arrays.asList("Location A", "Location B"));

        long count = parkingLotService.getDistinctLocationCount();

        assertEquals(2, count);
        verify(parkingLotRepository).findDistinctByLocation();
    }*/
}
