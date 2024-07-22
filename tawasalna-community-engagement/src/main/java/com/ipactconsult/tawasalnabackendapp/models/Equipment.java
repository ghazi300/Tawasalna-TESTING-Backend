package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "equipment")

public class Equipment {
    @Id
    private String id;
    private String name; // Nom de l'équipement
    private String description; // Description de l'équipement
    private String location; // Emplacement de l'équipement dans le centre de fitness
}
