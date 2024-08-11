package com.tawasalna.MaintenanceAgent.models;


import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "EnergieConsumption")
public class EnergieConsumption {


    @Id
    private String  id;
    private ComponentType type ; // Type de composant
    private String location; // Optionnel : Localisation dans le bâtiment
    private LocalDateTime timestamp;
    private Double amount; // Consommation en kWh
    private Double efficiencyRating;   // Note d'efficacité (peut être un pourcentage ou une autre mesure)


}
