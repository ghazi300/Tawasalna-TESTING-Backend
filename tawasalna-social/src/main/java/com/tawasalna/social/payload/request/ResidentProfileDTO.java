package com.tawasalna.social.payload.request;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResidentProfileDTO {
    private String residentId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Integer age;
    private String gender;
    private Date dateOfBirth;
    private String profilePhotoUrl;
    private String coverPhotoUrl;
    private String bio;
    private String accounttype;
}
