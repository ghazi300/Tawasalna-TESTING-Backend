package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "equipmentmaintenancelog")

public class EquipmentMaintenanceLog {
    @Id
    private String id;
    private String equipmentId; // ID de l'équipement
    private String technicianId; // ID du technicien en charge de la maintenance
    private Date maintenanceDate; // Date de la maintenance
    private String description; // Description de l'opération de maintenance
}
