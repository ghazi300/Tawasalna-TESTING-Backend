package com.example.tawasalnaoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hazardous_waste_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HazardousWasteLog {

    @Id
    private String id;
    private String wasteType;
    private String disposalMethod;
    private String quantity;
    private String disposalDate;
    private String compliantStatus;
}