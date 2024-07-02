package com.tawasalna.administration.payload.request;

import com.tawasalna.administration.models.enums.InvitationStatus;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDTO {
    private String id;
    private Users receiver;
    private Users sender;
    private String message;
    private InvitationStatus status;
}
