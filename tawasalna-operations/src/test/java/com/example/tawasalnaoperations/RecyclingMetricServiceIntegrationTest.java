package com.example.tawasalnaoperations;

import com.example.tawasalnaoperations.entities.MaterialType;
import com.example.tawasalnaoperations.entities.RecyclingMetric;
import com.example.tawasalnaoperations.repositories.RecyclingMetricRepository;
import com.example.tawasalnaoperations.services.RecyclingMetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class RecyclingMetricServiceIntegrationTest {

    @InjectMocks
    private RecyclingMetricService recyclingMetricService;

    @Mock
    private RecyclingMetricRepository recyclingMetricRepository;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        recyclingMetricRepository.deleteAll();
    }

    @Test
    public void testCreateRecyclingMetric() {
        RecyclingMetric metric = new RecyclingMetric();
        metric.setMetricId("1");
        metric.setType(MaterialType.PAPER);
        metric.setQuantity("100");

        when(recyclingMetricRepository.save(metric)).thenReturn(metric);

        RecyclingMetric created = recyclingMetricService.createRecyclingMetric(metric);

        assertNotNull(created);
        assertEquals("1", created.getMetricId());
        assertEquals(MaterialType.PAPER, created.getType());
        assertEquals("100", created.getQuantity());
        verify(recyclingMetricRepository, times(1)).save(metric);
    }

    @Test
    public void testGetAllMetrics() {
        RecyclingMetric metric1 = new RecyclingMetric();
        metric1.setMetricId("1");
        metric1.setType(MaterialType.PAPER);
        metric1.setQuantity("100");

        RecyclingMetric metric2 = new RecyclingMetric();
        metric2.setMetricId("2");
        metric2.setType(MaterialType.PLASTIC);
        metric2.setQuantity("200");

        List<RecyclingMetric> metrics = Arrays.asList(metric1, metric2);

        when(recyclingMetricRepository.findAll()).thenReturn(metrics);

        List<RecyclingMetric> found = recyclingMetricService.getAllMetrics();

        assertEquals(2, found.size());
        assertEquals("1", found.get(0).getMetricId());
        assertEquals("2", found.get(1).getMetricId());
        verify(recyclingMetricRepository, times(1)).findAll();
    }

    @Test
    public void testGetRecyclingStatistics() {
        RecyclingMetric metric1 = new RecyclingMetric();
        metric1.setMetricId("1");
        metric1.setType(MaterialType.PAPER);
        metric1.setQuantity("100");

        RecyclingMetric metric2 = new RecyclingMetric();
        metric2.setMetricId("2");
        metric2.setType(MaterialType.PAPER);
        metric2.setQuantity("200");

        RecyclingMetric metric3 = new RecyclingMetric();
        metric3.setMetricId("3");
        metric3.setType(MaterialType.PLASTIC);
        metric3.setQuantity("150");

        List<RecyclingMetric> metrics = Arrays.asList(metric1, metric2, metric3);

        when(recyclingMetricRepository.findAll()).thenReturn(metrics);

        Map<MaterialType, Long> stats = recyclingMetricService.getRecyclingStatistics();

        assertEquals(2, stats.size());
        assertEquals(300, stats.get(MaterialType.PAPER));
        assertEquals(150, stats.get(MaterialType.PLASTIC));
        verify(recyclingMetricRepository, times(1)).findAll();
    }

    @Test
    public void testGetRecyclingMetricById() {
        RecyclingMetric metric = new RecyclingMetric();
        metric.setMetricId("1");
        metric.setType(MaterialType.PAPER);
        metric.setQuantity("100");

        when(recyclingMetricRepository.findById("1")).thenReturn(Optional.of(metric));

        Optional<RecyclingMetric> found = recyclingMetricService.getRecyclingMetricById("1");

        assertTrue(found.isPresent());
        assertEquals("1", found.get().getMetricId());
        verify(recyclingMetricRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateRecyclingMetric() {
        RecyclingMetric existingMetric = new RecyclingMetric();
        existingMetric.setMetricId("1");
        existingMetric.setType(MaterialType.PAPER);
        existingMetric.setQuantity("100");

        RecyclingMetric updatedMetric = new RecyclingMetric();
        updatedMetric.setMetricId("1");
        updatedMetric.setType(MaterialType.PLASTIC);
        updatedMetric.setQuantity("150");

        when(recyclingMetricRepository.existsById("1")).thenReturn(true);
        when(recyclingMetricRepository.save(updatedMetric)).thenReturn(updatedMetric);

        RecyclingMetric updated = recyclingMetricService.updateRecyclingMetric("1", updatedMetric);

        assertNotNull(updated);
        assertEquals("1", updated.getMetricId());
        assertEquals(MaterialType.PLASTIC, updated.getType());
        assertEquals("150", updated.getQuantity());
        verify(recyclingMetricRepository, times(1)).save(updatedMetric);
    }

    @Test
    public void testDeleteRecyclingMetric() {
        String metricId = "1";

        doNothing().when(recyclingMetricRepository).deleteById(metricId);

        recyclingMetricService.deleteRecyclingMetric(metricId);

        verify(recyclingMetricRepository, times(1)).deleteById(metricId);
    }
}
