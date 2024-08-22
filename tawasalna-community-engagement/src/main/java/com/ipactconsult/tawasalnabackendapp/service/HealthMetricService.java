package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.HealthMetric;
import com.ipactconsult.tawasalnabackendapp.payload.request.HealthMetricRequest;
import com.ipactconsult.tawasalnabackendapp.repository.HealthMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor

public class HealthMetricService implements IHealthMetricService {
    private  final HealthMetricRepository healthMetricRepository;
    private final HealthMetricMapper healthMetricMapper;

    @Override
    public String save(HealthMetricRequest metricRequest) {
        HealthMetric healthMetric=healthMetricMapper.toHealthMetric(metricRequest);
        return healthMetricRepository.save(healthMetric).getId();
    }

    @Override
    public List<HealthMetric> getAllMetrics() {
        return healthMetricRepository.findAll();
    }

    @Override
    public void deleteHealth(String id) {
        healthMetricRepository.deleteById(id);
    }
}
