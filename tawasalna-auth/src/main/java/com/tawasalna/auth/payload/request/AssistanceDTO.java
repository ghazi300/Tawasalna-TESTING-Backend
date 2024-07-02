package com.tawasalna.auth.payload.request;

import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssistanceDTO {

    private String id;

    private Users agentID;

    private Users brokerID;

    private Users prospectID;
    private Users adminID;

    private AssistanceStatus status;

    private Date createdAt;

    private Date desiredDate;
    private String description;

}
