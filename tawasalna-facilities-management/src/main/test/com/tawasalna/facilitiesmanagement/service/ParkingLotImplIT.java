package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ParkingLotImplIT {
    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotImpl parkingLotImpl;


    private ParkingLot parkingLot;
    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot();
        parkingLot.setParkinglotid("1");
        parkingLot.setName("Main Lot");
        parkingLot.setLoacation("Downtown");
        parkingLot.setTotalspace(100);
    }

    @Test
    void getParkingLot() {
        when(parkingLotRepository.findAll()).thenReturn(List.of(parkingLot));

        List<ParkingLot> parkingLots = parkingLotImpl.getParkingLot();

        assertNotNull(parkingLots);
        assertFalse(parkingLots.isEmpty());
        assertEquals(1, parkingLots.size());
        assertEquals("Main Lot", parkingLots.get(0).getName());
    }

    @Test
    void add() {
        when(parkingLotRepository.save(parkingLot)).thenReturn(parkingLot);

        ParkingLot savedParkingLot = parkingLotImpl.add(parkingLot);

        assertNotNull(savedParkingLot);
        assertEquals("Main Lot", savedParkingLot.getName());
    }

    @Test
    void update() {
        ParkingLot updatedParkingLot = new ParkingLot();
        updatedParkingLot.setName("Updated Lot");
        updatedParkingLot.setLoacation("Uptown");
        updatedParkingLot.setTotalspace(150);

        when(parkingLotRepository.findById("1")).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(parkingLot)).thenReturn(parkingLot);

        ParkingLot result = parkingLotImpl.update("1", updatedParkingLot);

        assertNotNull(result);
        assertEquals("Updated Lot", result.getName());
        assertEquals("Uptown", result.getLoacation());
        assertEquals(150, result.getTotalspace());
    }

    @Test
    void delete() {
        when(parkingLotRepository.findById("1")).thenReturn(Optional.of(parkingLot));

        parkingLotImpl.delete("1");

        verify(parkingLotRepository, times(1)).deleteById("1");
    }

    @Test
    void getDistinctLocationCount() {
        when(parkingLotRepository.count()).thenReturn(5L);

        long count = parkingLotImpl.getDistinctLocationCount();

        assertEquals(5L, count);
    }
}