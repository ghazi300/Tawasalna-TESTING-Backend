package com.ipactconsult.tawasalnabackendapp.payload.response;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EquipmentMaintenanceLogResponse {
    private String id;
    private String equipmentId;
    private String technicianId;
    private Date maintenanceDate;
    private String description;
}
