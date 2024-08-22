package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.HealthMetric;
import com.ipactconsult.tawasalnabackendapp.payload.request.HealthMetricRequest;
import org.springframework.stereotype.Service;

@Service

public class HealthMetricMapper {
    public HealthMetric toHealthMetric(HealthMetricRequest metricRequest) {
        return  HealthMetric.builder()
                .participantId(metricRequest.getParticipantId())
                .metrics(metricRequest.getMetrics())


                .build();
    }
}
