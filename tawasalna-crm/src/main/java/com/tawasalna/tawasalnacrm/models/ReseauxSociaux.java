package com.tawasalna.tawasalnacrm.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reseaux_sociaux")
public class ReseauxSociaux {
    @Id
    private String id;
    private String name;
    private String url;
    private boolean archived; // New field added

    // Constructors, getters, setters

    // Constructors
    public ReseauxSociaux() {}

    public ReseauxSociaux(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // Getters and setters

    // Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Url
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Archived
    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}

