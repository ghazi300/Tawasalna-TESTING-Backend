package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "Technicien")
public class Technicien {

    @Id
    private String id;
    private Role role;
    private String yearsOfExperience;
    private String name;
    private String contactInfo;
    private String certification;
    private Status status;
    private List<String> assignedTaskId; // Nouvelle propriété optionnelle
}
