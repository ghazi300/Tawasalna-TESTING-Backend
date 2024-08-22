package com.example.residentsupportservices.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "pets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    private String id;

    private String name;
    private String type;
    private String breed;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // For incoming data
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  // For outgoing data
    private Date birthDate;
    private String imageUrl;

}
