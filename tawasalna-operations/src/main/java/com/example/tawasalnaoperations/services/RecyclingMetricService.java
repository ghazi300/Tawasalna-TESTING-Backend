package com.example.tawasalnaoperations.services;


import com.example.tawasalnaoperations.entities.RecyclingMetric;
import com.example.tawasalnaoperations.repositories.RecyclingMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingMetricService {

    @Autowired
    private RecyclingMetricRepository recyclingMetricRepository;

    // Create
    public RecyclingMetric createRecyclingMetric(RecyclingMetric metric) {
        return recyclingMetricRepository.save(metric);
    }

    // Read
    public List<RecyclingMetric> getAllRecyclingMetrics() {
        return recyclingMetricRepository.findAll();
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
