package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.WaterConsumptionSummaryController;
import com.tawasalna.MaintenanceAgent.models.WaterConsumptionSummary;
import com.tawasalna.MaintenanceAgent.repos.WaterConsumptionSummaryRepository;
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
import static org.mockito.Mockito.*;

public class WaterConsumptionSummaryControllerTest {

    @Mock
    private WaterConsumptionSummaryRepository waterConsumptionSummaryRepository;

    @InjectMocks
    private WaterConsumptionSummaryController waterConsumptionSummaryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllWaterConsumptionSummaries() {
        WaterConsumptionSummary summary1 = new WaterConsumptionSummary("1", new Date(), 100.0);
        WaterConsumptionSummary summary2 = new WaterConsumptionSummary("2", new Date(), 200.0);

        when(waterConsumptionSummaryRepository.findAll()).thenReturn(Arrays.asList(summary1, summary2));

        ResponseEntity<List<WaterConsumptionSummary>> response = waterConsumptionSummaryController.getAllWaterConsumptionSummaries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(waterConsumptionSummaryRepository, times(1)).findAll();
    }

    @Test
    public void testCreateWaterConsumptionSummary() {
        WaterConsumptionSummary summary = new WaterConsumptionSummary("1", new Date(), 100.0);

        when(waterConsumptionSummaryRepository.save(any(WaterConsumptionSummary.class))).thenReturn(summary);

        ResponseEntity<WaterConsumptionSummary> response = waterConsumptionSummaryController.createWaterConsumptionSummary(summary);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(summary, response.getBody());
        verify(waterConsumptionSummaryRepository, times(1)).save(summary);
    }

    @Test
    public void testGetWaterConsumptionSummaryById_Found() {
        WaterConsumptionSummary summary = new WaterConsumptionSummary("1", new Date(), 100.0);

        when(waterConsumptionSummaryRepository.findById("1")).thenReturn(Optional.of(summary));

        ResponseEntity<WaterConsumptionSummary> response = waterConsumptionSummaryController.getWaterConsumptionSummaryById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(summary, response.getBody());
        verify(waterConsumptionSummaryRepository, times(1)).findById("1");
    }

    @Test
    public void testGetWaterConsumptionSummaryById_NotFound() {
        when(waterConsumptionSummaryRepository.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<WaterConsumptionSummary> response = waterConsumptionSummaryController.getWaterConsumptionSummaryById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterConsumptionSummaryRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateWaterConsumptionSummary_Found() {
        WaterConsumptionSummary summary = new WaterConsumptionSummary("1", new Date(), 100.0);

        when(waterConsumptionSummaryRepository.existsById("1")).thenReturn(true);
        when(waterConsumptionSummaryRepository.save(any(WaterConsumptionSummary.class))).thenReturn(summary);

        ResponseEntity<WaterConsumptionSummary> response = waterConsumptionSummaryController.updateWaterConsumptionSummary("1", summary);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(summary, response.getBody());
        verify(waterConsumptionSummaryRepository, times(1)).existsById("1");
        verify(waterConsumptionSummaryRepository, times(1)).save(summary);
    }

    @Test
    public void testUpdateWaterConsumptionSummary_NotFound() {
        WaterConsumptionSummary summary = new WaterConsumptionSummary("1", new Date(), 100.0);

        when(waterConsumptionSummaryRepository.existsById("1")).thenReturn(false);

        ResponseEntity<WaterConsumptionSummary> response = waterConsumptionSummaryController.updateWaterConsumptionSummary("1", summary);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterConsumptionSummaryRepository, times(1)).existsById("1");
        verify(waterConsumptionSummaryRepository, times(0)).save(summary);
    }

    @Test
    public void testDeleteWaterConsumptionSummaryById_Found() {
        when(waterConsumptionSummaryRepository.existsById("1")).thenReturn(true);
        doNothing().when(waterConsumptionSummaryRepository).deleteById("1");

        ResponseEntity<Void> response = waterConsumptionSummaryController.deleteWaterConsumptionSummaryById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(waterConsumptionSummaryRepository, times(1)).existsById("1");
        verify(waterConsumptionSummaryRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteWaterConsumptionSummaryById_NotFound() {
        when(waterConsumptionSummaryRepository.existsById("1")).thenReturn(false);

        ResponseEntity<Void> response = waterConsumptionSummaryController.deleteWaterConsumptionSummaryById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(waterConsumptionSummaryRepository, times(1)).existsById("1");
        verify(waterConsumptionSummaryRepository, times(0)).deleteById("1");
    }

    @Test
    public void testDeleteAllWaterConsumptionSummaries() {
        doNothing().when(waterConsumptionSummaryRepository).deleteAll();

        ResponseEntity<Void> response = waterConsumptionSummaryController.deleteAllWaterConsumptionSummaries();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(waterConsumptionSummaryRepository, times(1)).deleteAll();
    }
}
