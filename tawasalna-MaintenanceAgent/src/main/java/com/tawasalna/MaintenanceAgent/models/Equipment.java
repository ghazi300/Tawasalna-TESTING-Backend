package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "equipements")
public class Equipment {
    @Id
    private String  id;
    private String equipmentName;
    private String category;
    private int quantityAvailable;
    private int minimumRequired;
    private String supplier;
    private LocalDate purchaseDate;
    private LocalDate lastReceivedDate;
    private BigDecimal unitPrice;
    private String location;
    private String status;
    private String serialNumber;
    private String comments;


}
