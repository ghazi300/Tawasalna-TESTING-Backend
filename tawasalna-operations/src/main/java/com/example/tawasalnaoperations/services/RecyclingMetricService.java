package com.example.tawasalnaoperations.services;


import com.example.tawasalnaoperations.entities.MaterialType;
import com.example.tawasalnaoperations.entities.RecyclingMetric;
import com.example.tawasalnaoperations.repositories.RecyclingMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecyclingMetricService {

    @Autowired
    private RecyclingMetricRepository recyclingMetricRepository;

    // Create
    public RecyclingMetric createRecyclingMetric(RecyclingMetric metric) {
        return recyclingMetricRepository.save(metric);
    }

    // Read
    public List<RecyclingMetric> getAllMetrics() {
        return recyclingMetricRepository.findAll();
    }

    public Map<MaterialType, Long> getRecyclingStatistics() {
        List<RecyclingMetric> metrics = recyclingMetricRepository.findAll();

        return metrics.stream()
                .collect(Collectors.groupingBy(RecyclingMetric::getType, Collectors.summingLong(metric -> Long.parseLong(metric.getQuantity()))));
    }
    public Optional<RecyclingMetric> getRecyclingMetricById(String metricId) {
        return recyclingMetricRepository.findById(metricId);
    }

    // Update
    public RecyclingMetric updateRecyclingMetric(String metricId, RecyclingMetric updatedMetric) {
        if (recyclingMetricRepository.existsById(metricId)) {
            updatedMetric.setMetricId(metricId);
            return recyclingMetricRepository.save(updatedMetric);
        } else {
            throw new RuntimeException("RecyclingMetric not found");
        }
    }

    // Delete
    public void deleteRecyclingMetric(String metricId) {
        recyclingMetricRepository.deleteById(metricId);
    }
}
