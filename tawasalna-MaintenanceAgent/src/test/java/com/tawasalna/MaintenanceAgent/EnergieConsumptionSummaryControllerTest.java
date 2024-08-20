package com.tawasalna.MaintenanceAgent;

import com.tawasalna.MaintenanceAgent.controllers.EnergieConsumptionSummaryController;
import com.tawasalna.MaintenanceAgent.models.EnergieConsumptionSummary;
import com.tawasalna.MaintenanceAgent.repos.EnergieConsumptionSummaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnergieConsumptionSummaryControllerTest {

    @Mock
    private EnergieConsumptionSummaryRepository energieConsumptionSummaryRepository;

    @InjectMocks
    private EnergieConsumptionSummaryController controller;

    private EnergieConsumptionSummary summary1;
    private EnergieConsumptionSummary summary2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        summary1 = new EnergieConsumptionSummary();
        summary1.setId("1");
        summary1.setDate(new Date());
        summary1.setTotalAmount(100.0);

        summary2 = new EnergieConsumptionSummary();
        summary2.setId("2");
        summary2.setDate(new Date());
        summary2.setTotalAmount(200.0);
    }

    @Test
    void getAllEnergieConsumptionSummaries() {
        List<EnergieConsumptionSummary> summaries = new ArrayList<>();
        summaries.add(summary1);
        summaries.add(summary2);

        when(energieConsumptionSummaryRepository.findAll()).thenReturn(summaries);

        ResponseEntity<List<EnergieConsumptionSummary>> response = controller.getAllEnergieConsumptionSummaries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(energieConsumptionSummaryRepository, times(1)).findAll();
    }

    @Test
    void deleteAllEnergieConsumptionSummaries() {
        ResponseEntity<Void> response = controller.deleteAllEnergieConsumptionSummaries();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(energieConsumptionSummaryRepository, times(1)).deleteAll();
    }
}
