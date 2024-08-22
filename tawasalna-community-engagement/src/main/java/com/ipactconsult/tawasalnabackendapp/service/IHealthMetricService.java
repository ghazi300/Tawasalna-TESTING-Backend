package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.HealthMetric;
import com.ipactconsult.tawasalnabackendapp.payload.request.HealthMetricRequest;

import java.util.List;

public interface IHealthMetricService {
    String save(HealthMetricRequest metricRequest);

    List<HealthMetric> getAllMetrics();

    void deleteHealth(String id);
}
