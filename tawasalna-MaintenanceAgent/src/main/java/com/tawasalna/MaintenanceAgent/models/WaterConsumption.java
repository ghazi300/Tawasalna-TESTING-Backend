package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "WaterConsumption")
public class WaterConsumption {

    @Id
    private String id;
    private WaterConsumptionType type; // Type de composant (e.g., plumbing component)
    private String location; // Optionnel : Localisation dans le bâtiment
    private Date timestamp;
    private Double volume; // Volume d'eau consommée (en litres ou m³)
    private Double efficiencyRating; // Note d'efficacité (peut être un pourcentage ou une autre mesure)
}
