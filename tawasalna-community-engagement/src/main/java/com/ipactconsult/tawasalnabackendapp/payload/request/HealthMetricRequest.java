package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HealthMetricRequest {
    @NotEmpty(message = "Participant ID must not be empty")
    @Size(max = 50, message = "Participant ID must not exceed 50 characters")
    private String participantId;



    @NotNull(message = "Metrics must not be null")
    private Map<@Pattern(regexp = "^[a-zA-Z ]+$", message = "Metric names must be alphabetic") String,
                @NotNull(message = "Metric values must not be null") Double> metrics;
}
