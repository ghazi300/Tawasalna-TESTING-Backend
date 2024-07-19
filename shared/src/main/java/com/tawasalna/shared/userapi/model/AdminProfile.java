package com.tawasalna.shared.userapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfile {
    private String firstname;
    private String lastname;
    private String title;
    private String address;
    private String city;
    private String postalCode;
    private String professionalEmail;
    private String professionalPhone;
    private String profileImage;
}
