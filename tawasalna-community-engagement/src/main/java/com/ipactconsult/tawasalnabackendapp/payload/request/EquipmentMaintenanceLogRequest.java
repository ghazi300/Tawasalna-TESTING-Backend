package com.ipactconsult.tawasalnabackendapp.payload.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
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

    @NotBlank(message = "Description is required")
    private String description;



    @NotNull(message = "Next scheduled date is required")
    @FutureOrPresent(message = "Next scheduled date cannot be in the past")
    private LocalDateTime nextScheduledDate;
}
