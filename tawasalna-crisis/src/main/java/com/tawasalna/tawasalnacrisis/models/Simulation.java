package com.tawasalna.tawasalnacrisis.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
@Document(collection = "Simulations")
public class Simulation {
    @Id
    private String id;
    private String titre;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private String status;
    @LastModifiedDate
    private Date lastUpdated;
    @DBRef
    PlanUrgence planUrgence;
}
