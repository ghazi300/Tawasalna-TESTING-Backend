package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import org.springframework.data.annotation.Id;
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


    @Id
    private String  id;


    private String maintenanceTaskId;
    private List<String> technicienId ;
    private TaskStatus taskStatus;
    private Date dateDebut;
    private Date dateFin;
    private List<Equipment> equipements; // Updated to use Equipment class

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Equipment {
        private String equipmentId;
        private String equipmentName;
        private int quantity;
    }
}
