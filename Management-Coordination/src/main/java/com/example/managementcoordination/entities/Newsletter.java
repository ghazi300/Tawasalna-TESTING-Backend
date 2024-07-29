package com.example.managementcoordination.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "newsletters")
public class Newsletter {
    @Id
    private String id;
    private String subject;
    private String filePath; // Store the path or reference to the data

    public void setFilePath(String dataPath) {
        this.filePath = dataPath;
    }
}
