package com.tawasalna.shared.verificationrequestapi.model;

import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "verificationRequests")
public class VerificationRequest {

    @Id
    private String id;

    private String documentName;

    @DocumentReference
    private Users requester;

    @DocumentReference
    private Community community;

    private Boolean isAccepted = false;

    private LocalDateTime acceptedAt;

    private Boolean isArchived = false;
}
