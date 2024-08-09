package com.tawasalnasecuritysafety.models;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "correctiveaction-tawasalna")
@Tag(name = "correctiveaction")
public class CorrectiveAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String description;
    private String responsibleParty;
    private Date deadline;
    private String status; //  "Pending", "Completed"

    public void setDeadline(String date) {
        if (date != null && !date.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.deadline = sdf.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("Failed to parse date: " + date, e);
            }
        }
    }

    // Directly set the description
    public void setActionDescription(String description) {
        this.description = description;
    }

    // Directly get the description
    public String getActionDescription() {
        return this.description;
    }
}
