package com.tawasalna.tawasalnacrm.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "about")
public class About {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean archived; // Nouveau champ ajouté

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isArchived() { // Getter pour le champ archived
        return archived;
    }

    public void setArchived(boolean archived) { // Setter pour le champ archived
        this.archived = archived;
    }
}
