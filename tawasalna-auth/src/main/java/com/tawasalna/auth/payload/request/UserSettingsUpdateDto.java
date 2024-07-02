package com.tawasalna.auth.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSettingsUpdateDto {

    private String preferredTheme;
    private String preferredLocale;
    private Boolean isNotificationsActive;
    private Boolean isEmailVis;
    private Boolean isFullNameVis;
}
