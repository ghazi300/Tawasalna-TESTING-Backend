package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "AsignedTasks")
public class AsignedTask {



    private MaintenanceTask maintenanceTask;
    private List<Technicien> techniciens ;
    private TaskStatus taskStatus;
    private Date dateDebut;
    private Date dateFin;
    private List<String> equipements;
}
