package com.tawasalna.tawasalnacrisis.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "resources")
@Data // Génère les getters, setters, toString, equals, et hashCode
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
public class Resource {

    @Id
    private String id;  // L'identifiant unique pour MongoDB

    private String name;
    private String type;
    private String status;
    private String location;

}
