package com.example.managementcoordination.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
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
    private String content;
    private List<String> recipients;
    private Date sentDate;
    private String filePath;
    private int responseCount;
}
