package com.tawasalna.auth.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshTokenDTO {

    @NotBlank
    String expiredToken;
}
