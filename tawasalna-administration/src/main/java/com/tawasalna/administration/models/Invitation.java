package com.tawasalna.administration.models;

import com.tawasalna.administration.models.enums.InvitationStatus;
import com.tawasalna.shared.userapi.model.Users;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "invitation")
public class Invitation {
    @Id
    private String id;
    @DocumentReference
    private Users receiver;
    @DocumentReference
    private Users sender;
    private String message;
    private InvitationStatus status;
}
