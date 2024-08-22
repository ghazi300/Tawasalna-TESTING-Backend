package com.ipactconsult.tawasalnabackendapp.controller;
import com.ipactconsult.tawasalnabackendapp.controllers.HealthMetricController;
import com.ipactconsult.tawasalnabackendapp.models.HealthMetric;
import com.ipactconsult.tawasalnabackendapp.payload.request.HealthMetricRequest;
import com.ipactconsult.tawasalnabackendapp.service.IHealthMetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
public class HealthMetricControllerTest {
    @InjectMocks
    private HealthMetricController healthMetricController;

    @Mock
    private IHealthMetricService healthMetricService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMetric() {
        HealthMetricRequest metricRequest = new HealthMetricRequest(); // Initialize as needed
        String metricId = "metricId123";

        when(healthMetricService.save(metricRequest)).thenReturn(metricId);

        ResponseEntity<String> response = healthMetricController.addMetric(metricRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(metricId, response.getBody());
    }



    @Test
    void testGetAllMetrics() {
        HealthMetric metric1 = new HealthMetric(); // Initialize as needed
        HealthMetric metric2 = new HealthMetric(); // Initialize as needed
        List<HealthMetric> metrics = Arrays.asList(metric1, metric2);

        when(healthMetricService.getAllMetrics()).thenReturn(metrics);

        ResponseEntity<List<HealthMetric>> response = healthMetricController.getAllMetrics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(metrics, response.getBody());
    }

    @Test
    void testDeleteHealth() {
        String id = "metricId123";

        ResponseEntity<Void> response = healthMetricController.deleteHealth(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
