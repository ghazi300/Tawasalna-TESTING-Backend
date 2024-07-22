package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FitnessCenterUsageRequest {
    @NotBlank(message = "Equipment ID is mandatory")
    private String equipmentId;

    @NotBlank(message = "User ID is mandatory")
    private String userId;

    @NotNull(message = "Start time is mandatory")
    @Min(value = 0, message = "Start time must be a positive value")
    private Long startTime;

    @NotNull(message = "End time is mandatory")
    @Min(value = 0, message = "End time must be a positive value")
    private Long endTime;
}
