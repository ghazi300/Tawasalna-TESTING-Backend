package com.ipactconsult.tawasalnabackendapp.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FitnessCenterUsageResponse {
    private String id;
    private String equipmentId; // ID of the used equipment
    private String userId; // ID of the user using the equipment
    private long startTime; // Start time of the usage (timestamp in milliseconds)
    private long endTime; // End time of the usage (timestamp in milliseconds)
    private String equipmentName; // Name of the equipment (resolved from equipmentId)
    private String equipmentDescription; // Description of the equipment (resolved from equipmentId)
}
