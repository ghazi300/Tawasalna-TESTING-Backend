package com.example.tawasalnaoperations.entities;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Type;

@Document(collection = "recycling_metric")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecyclingMetric {
    @Id
    private String metricId;

    @Enumerated(EnumType.STRING)
    private MaterialType type;
    private String quantity;
}
