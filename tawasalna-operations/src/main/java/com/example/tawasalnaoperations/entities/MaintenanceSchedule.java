package com.example.tawasalnaoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
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
    private Date maintenanceDate;
    private List<String> tasks;
    private String status;
}