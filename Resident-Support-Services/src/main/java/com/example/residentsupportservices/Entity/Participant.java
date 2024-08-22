package com.example.residentsupportservices.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "participant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @Id
    private String id;

    private String name;
    private boolean attended;
    private Integer age;
    private String email;
    private String phone;
    private String address;
    private String specialNeeds;
}
