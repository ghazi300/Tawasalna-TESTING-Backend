package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.WaterConsumptionController;

import com.tawasalna.MaintenanceAgent.models.WaterConsumption;
import com.tawasalna.MaintenanceAgent.models.WaterConsumptionType;
import com.tawasalna.MaintenanceAgent.repos.WaterConsumptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WaterConsumptionControllerTest {

    @Mock
    private WaterConsumptionRepository waterConsumptionRepository;

    @InjectMocks
    private WaterConsumptionController waterConsumptionController;

    private WaterConsumption waterConsumption;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        waterConsumption = new WaterConsumption();
        waterConsumption.setId("1");
        waterConsumption.setType(WaterConsumptionType.AUTRE);
        waterConsumption.setLocation("Building A");
        waterConsumption.setTimestamp(new Date());
        waterConsumption.setVolume(100.0);
        waterConsumption.setEfficiencyRating(80.0);
    }

    @Test
    void testGetAllWaterConsumptions() {
        List<WaterConsumption> waterConsumptions = Arrays.asList(waterConsumption);
        when(waterConsumptionRepository.findAll()).thenReturn(waterConsumptions);

        ResponseEntity<List<WaterConsumption>> response = waterConsumptionController.getAllWaterConsumptions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(waterConsumptionRepository, times(1)).findAll();
    }

    @Test
    void testCreateWaterConsumption() {
        when(waterConsumptionRepository.save(any(WaterConsumption.class))).thenReturn(waterConsumption);

        ResponseEntity<WaterConsumption> response = waterConsumptionController.createWaterConsumption(waterConsumption);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(waterConsumption, response.getBody());
        verify(waterConsumptionRepository, times(1)).save(any(WaterConsumption.class));
    }

    @Test
    void testGetWaterConsumptionById() {
        when(waterConsumptionRepository.findById(anyString())).thenReturn(Optional.of(waterConsumption));

        ResponseEntity<WaterConsumption> response = waterConsumptionController.getWaterConsumptionById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(waterConsumption, response.getBody());
        verify(waterConsumptionRepository, times(1)).findById("1");
    }

    @Test
    void testGetWaterConsumptionById_NotFound() {
        when(waterConsumptionRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<WaterConsumption> response = waterConsumptionController.getWaterConsumptionById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterConsumptionRepository, times(1)).findById("1");
    }

    @Test
    void testUpdateWaterConsumption() {
        when(waterConsumptionRepository.existsById(anyString())).thenReturn(true);
        when(waterConsumptionRepository.save(any(WaterConsumption.class))).thenReturn(waterConsumption);

        ResponseEntity<WaterConsumption> response = waterConsumptionController.updateWaterConsumption("1", waterConsumption);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(waterConsumption, response.getBody());
        verify(waterConsumptionRepository, times(1)).existsById("1");
        verify(waterConsumptionRepository, times(1)).save(waterConsumption);
    }

    @Test
    void testUpdateWaterConsumption_NotFound() {
        when(waterConsumptionRepository.existsById(anyString())).thenReturn(false);

        ResponseEntity<WaterConsumption> response = waterConsumptionController.updateWaterConsumption("1", waterConsumption);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterConsumptionRepository, times(1)).existsById("1");
        verify(waterConsumptionRepository, times(0)).save(waterConsumption);
    }

    @Test
    void testDeleteAllWaterConsumptions() {
        doNothing().when(waterConsumptionRepository).deleteAll();

        ResponseEntity<Void> response = waterConsumptionController.deleteAllWaterConsumptions();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(waterConsumptionRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteWaterConsumptionById() {
        when(waterConsumptionRepository.existsById(anyString())).thenReturn(true);
        doNothing().when(waterConsumptionRepository).deleteById(anyString());

        ResponseEntity<Void> response = waterConsumptionController.deleteWaterConsumptionById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(waterConsumptionRepository, times(1)).existsById("1");
        verify(waterConsumptionRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteWaterConsumptionById_NotFound() {
        when(waterConsumptionRepository.existsById(anyString())).thenReturn(false);

        ResponseEntity<Void> response = waterConsumptionController.deleteWaterConsumptionById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterConsumptionRepository, times(1)).existsById("1");
        verify(waterConsumptionRepository, times(0)).deleteById("1");
    }
}
