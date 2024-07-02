package com.tawasalna.auth.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrokerProfile {
    private String firstname;
    private String lastname;
    private String title;
    private String address;
    private String city;
    private String postalCode;
    private String professionalEmail;
    private String professionalPhone;
    private String logoName;
    private String profileImage;
    private String telnumber;


}
