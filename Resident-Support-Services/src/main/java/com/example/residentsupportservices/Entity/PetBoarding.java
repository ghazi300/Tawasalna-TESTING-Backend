package com.example.residentsupportservices.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "pet_boardings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetBoarding {
    @Id
    private String id;
    private String petId; // Référence à l'ID de l'animal
    private String ownerName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String notes;
}