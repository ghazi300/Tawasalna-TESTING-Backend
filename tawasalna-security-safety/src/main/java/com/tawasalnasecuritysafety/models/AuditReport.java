package com.tawasalnasecuritysafety.models;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "auditreport-tawasalna")
@Tag(name = "auditreport")
public class AuditReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String summary;
    private String status; // "Completed", "Pending"
    private Date date;

    @Lob
    private String findings;
    @Lob
    private String recommendations;

    // Getters and Setters
}
