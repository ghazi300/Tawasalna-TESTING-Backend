package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "equipmentmaintenancelog")

public class EquipmentMaintenanceLog {
    private String id;
    private String equipmentId;
    private String technicianId;
    private String description;
    @CreatedDate
    private LocalDateTime maintenanceDate;
    private LocalDateTime nextScheduledDate;
}
