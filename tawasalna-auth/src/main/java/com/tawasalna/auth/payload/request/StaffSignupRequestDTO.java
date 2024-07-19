package com.tawasalna.auth.payload.request;

import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.StaffSignupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StaffSignupRequestDTO {
    private String id;
    private Users agent;
    private Users broker;
    private List<Users> admins;

    private Date createdAt;
    private StaffSignupStatus status;
    private String description;

}
