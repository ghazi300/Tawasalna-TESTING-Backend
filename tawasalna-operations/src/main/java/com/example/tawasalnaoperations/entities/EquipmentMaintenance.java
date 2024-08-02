package com.example.tawasalnaoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "equipment_maintenance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentMaintenance {
    @Id
    private String maintenanceId;
    private String equipmentId;
    private Date maintenanceDate;
    private String tasksPerformed;
    private String status;


}
