package com.tawasalna.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDTO {

    @NotBlank
    @Size(min = 8)
    private String oldPassword;

    @NotBlank
    @Size(min = 8)
    private String newPassword;
}
