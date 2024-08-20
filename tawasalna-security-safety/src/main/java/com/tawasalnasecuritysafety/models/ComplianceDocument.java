package com.tawasalnasecuritysafety.models;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "compliancedocument-tawasalna")
@Tag(name = "compliancedocument")
public class ComplianceDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "compliance_id")
    private Compliance compliance;

}
