package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EquipmentMaintenanceLogRequest {
    @NotBlank(message = "Equipment ID is required")
    private String equipmentId;

    @NotBlank(message = "Technician ID is required")
    private String technicianId;

    @NotNull(message = "Maintenance date is required")
    private Date maintenanceDate;

    @NotBlank(message = "Description is required")
    private String description;
}
