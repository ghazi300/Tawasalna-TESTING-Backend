package com.tawasalna.auth.payload.request;

import com.tawasalna.auth.models.enums.Competence;
import com.tawasalna.auth.models.enums.Specialisation;
import com.tawasalna.shared.communityapi.model.Community;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPmsDTO {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String telnumber;

    @NotBlank
    private String role;
    private String assistentMail;
    private String loginLink;
    private String professionalEmail;
    private String professionalPhone;
    private String communityId;
}
