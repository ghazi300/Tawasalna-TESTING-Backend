package com.example.tawasalnaoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "landscaping_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceSchedule {
    @Id
    private String scheduleId;
    private String gardenId;
    private Date dateDebut;
    private Date dateFin;
    private List<String> tasks;
}