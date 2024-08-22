package com.ipactconsult.tawasalnabackendapp.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EquipmentMaintenanceLogResponse {
    private String id;
    private String equipmentId;
    private String technicianId;
    private LocalDateTime maintenanceDate;
    private String description;
    private LocalDateTime nextScheduledDate;
}
