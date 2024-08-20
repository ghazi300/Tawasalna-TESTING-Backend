package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.WaterSourceController;
import com.tawasalna.MaintenanceAgent.models.WaterConsumptionType;
import com.tawasalna.MaintenanceAgent.models.WaterSource;
import com.tawasalna.MaintenanceAgent.repos.WaterSourceRepository;
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

class WaterSourceControllerTest {

    @Mock
    private WaterSourceRepository waterSourceRepository;

    @InjectMocks
    private WaterSourceController waterSourceController;

    private WaterSource waterSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        waterSource = new WaterSource();
        waterSource.setId("1");
        waterSource.setType(WaterConsumptionType.AUTRE);
        waterSource.setLocation("Site A");
        waterSource.setTimestamp(new Date());
        waterSource.setVolume(500.0);
        waterSource.setEfficiencyRating(85.0);
    }

    @Test
    void testGetAllWaterSources() {
        List<WaterSource> waterSources = Arrays.asList(waterSource);
        when(waterSourceRepository.findAll()).thenReturn(waterSources);

        ResponseEntity<List<WaterSource>> response = waterSourceController.getAllWaterSources();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(waterSourceRepository, times(1)).findAll();
    }

    @Test
    void testCreateWaterSource() {
        when(waterSourceRepository.save(any(WaterSource.class))).thenReturn(waterSource);

        ResponseEntity<WaterSource> response = waterSourceController.createWaterSource(waterSource);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(waterSource, response.getBody());
        verify(waterSourceRepository, times(1)).save(any(WaterSource.class));
    }

    @Test
    void testGetWaterSourceById() {
        when(waterSourceRepository.findById(anyString())).thenReturn(Optional.of(waterSource));

        ResponseEntity<WaterSource> response = waterSourceController.getWaterSourceById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(waterSource, response.getBody());
        verify(waterSourceRepository, times(1)).findById("1");
    }

    @Test
    void testGetWaterSourceById_NotFound() {
        when(waterSourceRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<WaterSource> response = waterSourceController.getWaterSourceById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterSourceRepository, times(1)).findById("1");
    }

    @Test
    void testUpdateWaterSource() {
        when(waterSourceRepository.existsById(anyString())).thenReturn(true);
        when(waterSourceRepository.save(any(WaterSource.class))).thenReturn(waterSource);

        ResponseEntity<WaterSource> response = waterSourceController.updateWaterSource("1", waterSource);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(waterSource, response.getBody());
        verify(waterSourceRepository, times(1)).existsById("1");
        verify(waterSourceRepository, times(1)).save(waterSource);
    }

    @Test
    void testUpdateWaterSource_NotFound() {
        when(waterSourceRepository.existsById(anyString())).thenReturn(false);

        ResponseEntity<WaterSource> response = waterSourceController.updateWaterSource("1", waterSource);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterSourceRepository, times(1)).existsById("1");
        verify(waterSourceRepository, times(0)).save(waterSource);
    }

    @Test
    void testDeleteAllWaterSources() {
        doNothing().when(waterSourceRepository).deleteAll();

        ResponseEntity<Void> response = waterSourceController.deleteAllWaterSources();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(waterSourceRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteWaterSourceById() {
        when(waterSourceRepository.existsById(anyString())).thenReturn(true);
        doNothing().when(waterSourceRepository).deleteById(anyString());

        ResponseEntity<Void> response = waterSourceController.deleteWaterSourceById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(waterSourceRepository, times(1)).existsById("1");
        verify(waterSourceRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteWaterSourceById_NotFound() {
        when(waterSourceRepository.existsById(anyString())).thenReturn(false);

        ResponseEntity<Void> response = waterSourceController.deleteWaterSourceById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterSourceRepository, times(1)).existsById("1");
        verify(waterSourceRepository, times(0)).deleteById("1");
    }
}
