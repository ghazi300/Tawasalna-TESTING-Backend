package com.tawasalna.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tawasalna.auth.models.enums.AppTheme;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserSettings {

    @Id
    private String id;

    private AppTheme preferredTheme;

    private String preferredLocale;

    private Boolean isNotificationsActive;

    private PrivacySettings privacySettings;

    @JsonIgnore
    @DocumentReference
    private Users user;
}
