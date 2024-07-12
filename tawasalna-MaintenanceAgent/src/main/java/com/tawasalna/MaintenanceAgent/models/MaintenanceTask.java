package com.tawasalna.MaintenanceAgent.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "MaintenanceTask")
public class MaintenanceTask {

    @Id
    private String  id;

    private String description;
    private String type;
    private String status;
    private String priority;
    private Date createdAt;
    private String comments;
}
