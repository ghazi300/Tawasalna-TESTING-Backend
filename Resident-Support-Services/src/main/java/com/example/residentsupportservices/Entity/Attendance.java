package com.example.residentsupportservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.example.residentsupportservices.entity.Event;
import com.example.residentsupportservices.entity.Participant;
@Document(collection = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    private String id;


    @DBRef // Use DBRef for referencing other MongoDB documents
    private Event event;
    @DBRef
    private Participant participantName;
    private Boolean attended;
}
