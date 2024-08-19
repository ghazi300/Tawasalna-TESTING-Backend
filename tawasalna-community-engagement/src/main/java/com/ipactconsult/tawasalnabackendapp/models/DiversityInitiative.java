package com.ipactconsult.tawasalnabackendapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "diversityInitiatives")

public class DiversityInitiative {
    @Id
    private String id;
    private String title;
    private String description;
    private String lead;
    private Date startDate;
    private Date endDate;
    private DiversityInitiativeStatus status;
}
