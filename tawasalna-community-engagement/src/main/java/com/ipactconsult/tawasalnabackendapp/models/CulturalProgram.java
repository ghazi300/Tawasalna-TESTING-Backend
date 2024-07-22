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
@Document(collection = "culturalPrograms")

public class CulturalProgram {
    @Id
    private String id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private String coordinator;
}
