package com.example.tawasalnaoperations.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "pest_control")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PestControlReport {
    @Id

    private String reportId;
    private String gardenId;
    private Date reportDate;
    private List<String> pestsIdentified;
    private List<String> actionsTaken;
    private String status;


}
