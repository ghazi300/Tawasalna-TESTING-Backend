package com.tawasalna.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneUpdateDTO {

    @NotBlank(message = "Phone number cannot be blank!")
    private String phoneNumber;

    @NotBlank(message = "Phone number cannot be blank!")
    private String code;
}
