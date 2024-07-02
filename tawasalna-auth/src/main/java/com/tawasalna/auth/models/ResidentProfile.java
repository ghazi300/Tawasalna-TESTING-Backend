package com.tawasalna.auth.models;

import com.tawasalna.auth.models.enums.AccountType;
import com.tawasalna.auth.models.enums.Gender;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResidentProfile {

    private String residentId;
    private String fullName;
    private String phoneNumber;

    private String address;
    private Integer age;
    private Date dateOfBirth;
    private String bio;
    private Gender gender;
    private String profilephoto;
    private String coverphoto;

    private String verificationCode;
    private AccountType accountType;
    private List<String> followers = new ArrayList<>();
    private List<String> following = new ArrayList<>();
}
