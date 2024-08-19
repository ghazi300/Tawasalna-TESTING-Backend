package com.example.residentsupportservices.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "childcare_programs")
public class ChildcareProgram {

    @Id
    private String id;
    private String name;
    private String description;
    private String ageGroup;
    private Date startDate;
    private Date endDate;
    private String status;

    // Constructeur sans arguments
    public ChildcareProgram() {}

    // Constructeur avec tous les arguments
    public ChildcareProgram(String id, String name, String description, String ageGroup, Date startDate, Date endDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ageGroup = ageGroup;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
