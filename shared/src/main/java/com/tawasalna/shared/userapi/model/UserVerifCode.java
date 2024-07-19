package com.tawasalna.shared.userapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document(collection = "userVerifCodes")
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifCode {

    @Id
    private String id;

    @NotBlank
    private String code;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    private LocalDateTime expiredAt;
}
