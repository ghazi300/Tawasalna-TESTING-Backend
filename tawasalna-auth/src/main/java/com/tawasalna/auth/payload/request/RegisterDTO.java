package com.tawasalna.auth.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @Email
    @Size(max = 50)
    @NotBlank(message = "Email is required")
    private String email;
    private String secondaryEmail = "";

    @NotBlank
    @NotBlank(message = "Password is required !")
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String password;

    @NotBlank(message = "Role is required !")
    private String role;

    private String address;
    private String fullname;
    private Date dateOfBirth;
    private String matriculate;
    private String residentId;
    private String businessName;

    @Email
    private String professionalEmail;
    private String professionalPhone;
    private String website;
    private String linkedin;
    private String city;
    private String country;
    private String postalCode;

}
