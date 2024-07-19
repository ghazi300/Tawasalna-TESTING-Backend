package com.tawasalna.auth.models;

import lombok.*;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PrivacySettings {

    private Boolean isEmailVis;
    private Boolean isFullNameVis;
}
