package com.tawasalna.auth.models;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProspectProfile {
    private String emailAssistant;
    private String firstname;
    private String lastname;
    private String title;
    private String address;
    private String city;
    private String postalCode;
    private String profileImage;


}
