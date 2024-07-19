package com.tawasalna.auth.models;

import com.tawasalna.auth.models.enums.StaffSignupStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Document(collection = "staffSignupRequest")
public class StaffSignupRequest {
    @Id
    private String id;
    @DocumentReference
    private Users agent;
    @DocumentReference
    private Users broker;
    private List<Users> admins;
    private Date createdAt;
    private StaffSignupStatus status;
    private String description;

}
