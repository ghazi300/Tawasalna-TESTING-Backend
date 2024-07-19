package com.tawasalna.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessProfile {

    private String businessName;
    private String professionalPhone;
    private String professionalEmail;
    private String logoName;
    private String website;
    private String domain;
    private String linkedin;
    private String country;
    private String city;
    private String postalCode;
    private String matriculate;
    private String coverPhotoName;

    private boolean verified = false;
}
