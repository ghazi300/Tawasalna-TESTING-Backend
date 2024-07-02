package com.tawasalna.auth.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProspectProfileDTO {
    private String firstname;
    private String lastname;
    private String title;
    private String address;
    private String city;
    private String postalCode;
    private String agent;
    private String broker;

}
