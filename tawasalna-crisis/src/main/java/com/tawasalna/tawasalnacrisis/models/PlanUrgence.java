package com.tawasalna.tawasalnacrisis.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "PlansUrgence")
public class PlanUrgence {
    @Id
    private String id;
    private String titre;
    private String description;
    private String etapes;
    private String ressourcesNecessaires;
    @LastModifiedDate
    private Date lastUpdated;
    private List<String> images;

}
