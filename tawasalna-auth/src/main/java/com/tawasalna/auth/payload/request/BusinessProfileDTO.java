package com.tawasalna.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessProfileDTO {

    @NotBlank
    private String businessName;

    @Email
    @NotBlank
    private String professionalEmail;

    @NotBlank
    private String address;

    @NotBlank
    private String website;

    @NotBlank
    private String linkedin;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;

    private String matriculate;
}
