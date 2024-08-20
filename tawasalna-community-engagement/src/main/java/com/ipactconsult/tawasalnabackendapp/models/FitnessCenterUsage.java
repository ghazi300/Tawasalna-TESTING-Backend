package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "fitness_center_usage")

public class FitnessCenterUsage {
    @Id
    private String id;
    private String equipmentId; // ID de l'équipement utilisé
    private String userId; // ID de l'utilisateur utilisant l'équipement
    private long startTime; // Heure de début de l'utilisation (timestamp en millisecondes)
    private long endTime; // Heure de fin de l'utilisation (timestamp en millisecondes)
    private long duration;
}
