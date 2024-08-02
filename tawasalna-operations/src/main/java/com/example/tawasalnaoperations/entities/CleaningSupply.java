package com.example.tawasalnaoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "cleaning_supplies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CleaningSupply {

    @Id
    private String supplyId;
    private String itemName;
    private int quantity;
    private String category;
   // private LocalDateTime lastUpdated;
}
