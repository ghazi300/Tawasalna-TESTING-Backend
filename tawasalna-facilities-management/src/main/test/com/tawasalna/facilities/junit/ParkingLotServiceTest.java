package com.tawasalna.facilities.junit;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.repository.ParkingLotRepository;
import com.tawasalna.facilitiesmanagement.service.ParkingLotImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class ParkingLotServiceTest {


    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotImpl parkingLotService;

    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parkingLot = new ParkingLot("1", "Lot A", "Location A", 12.345, 67.890, 100, Arrays.asList("Route1", "Route2"), null, null);
    }

    @Test
    void getParkingLot_shouldReturnParkingLots() {
        when(parkingLotRepository.findAll()).thenReturn(Arrays.asList(parkingLot));

        List<ParkingLot> result = parkingLotService.getParkingLot();

        assertEquals(1, result.size());
        assertEquals(parkingLot, result.get(0));
    }

    @Test
    void addParkingLot_shouldSaveAndReturnParkingLot() {
        when(parkingLotRepository.save(parkingLot)).thenReturn(parkingLot);

        ParkingLot result = parkingLotService.add(parkingLot);

        assertEquals(parkingLot, result);
        verify(parkingLotRepository, times(1)).save(parkingLot);
    }

    @Test
    void updateParkingLot_shouldUpdateAndReturnUpdatedParkingLot() {
        ParkingLot updatedParkingLot = new ParkingLot("1", "Lot B", "Location B", 12.345, 67.890, 150, Arrays.asList("Route3"), null, null);

        when(parkingLotRepository.findById(parkingLot.getParkinglotid())).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(updatedParkingLot);

        ParkingLot result = parkingLotService.update(parkingLot.getParkinglotid(), updatedParkingLot);

        assertEquals(updatedParkingLot.getName(), result.getName());
        assertEquals(updatedParkingLot.getLoacation(), result.getLoacation());
        assertEquals(updatedParkingLot.getTotalspace(), result.getTotalspace());
        verify(parkingLotRepository, times(1)).save(any(ParkingLot.class));
    }

    @Test
    void updateParkingLot_shouldThrowExceptionWhenParkingLotNotFound() {
        when(parkingLotRepository.findById(parkingLot.getParkinglotid())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> parkingLotService.update(parkingLot.getParkinglotid(), parkingLot));
        verify(parkingLotRepository, times(0)).save(any(ParkingLot.class));
    }

    @Test
    void deleteParkingLot_shouldDeleteParkingLot() {
        when(parkingLotRepository.findById(parkingLot.getParkinglotid())).thenReturn(Optional.of(parkingLot));

        parkingLotService.delete(parkingLot.getParkinglotid());

        verify(parkingLotRepository, times(1)).deleteById(parkingLot.getParkinglotid());
    }

    @Test
    void deleteParkingLot_shouldThrowExceptionWhenParkingLotNotFound() {
        when(parkingLotRepository.findById(parkingLot.getParkinglotid())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> parkingLotService.delete(parkingLot.getParkinglotid()));
        verify(parkingLotRepository, times(0)).deleteById(parkingLot.getParkinglotid());
    }

    @Test
    void getDistinctLocationCount_shouldReturnCount() {
        when(parkingLotRepository.count()).thenReturn(5L);

        long count = parkingLotService.getDistinctLocationCount();

        assertEquals(5, count);
        verify(parkingLotRepository, times(1)).count();
    }

}
