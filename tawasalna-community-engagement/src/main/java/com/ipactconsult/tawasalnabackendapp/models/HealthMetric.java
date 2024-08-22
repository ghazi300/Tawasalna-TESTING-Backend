package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "health_metrics")

public class HealthMetric {
    @Id
    private String id;
    private String participantId;
    @CreatedDate
    private LocalDate date;
    private Map<String, Double> metrics;
}
