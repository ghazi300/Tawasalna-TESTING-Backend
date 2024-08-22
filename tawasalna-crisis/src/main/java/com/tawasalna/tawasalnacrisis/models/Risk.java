package com.tawasalna.tawasalnacrisis.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "risks")
public class Risk {
    @Id
    private String id;
    private String title;
    private String description;
    private String severity;
    private LocalDate identifiedDate;
    private boolean mitigated;
    private List<PlanUrgence> mitigationPlans;

    // Getters and Setters
}
