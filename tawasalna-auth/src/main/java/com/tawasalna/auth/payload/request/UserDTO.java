package com.tawasalna.auth.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String email;
    private String answer1;
    private String answer2;
    private String answer3;
    private String username;
    private String password;
}
