package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "EnergySource")
public class EnergySource {

    @Id
    private String id;
    private ComponentType type ; // Type de composant
    private String location; // Location of the energy source
    private Date timestamp;
    private Double amount; // Energy produced in kWh
    private Double efficiencyRating; // Efficiency rating of the source

}
