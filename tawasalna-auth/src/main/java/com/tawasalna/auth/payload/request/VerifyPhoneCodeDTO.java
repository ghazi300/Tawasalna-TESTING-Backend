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
public class VerifyPhoneCodeDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String phone;
}
