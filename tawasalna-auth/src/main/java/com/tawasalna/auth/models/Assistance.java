package com.tawasalna.auth.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Document(collection="assistance")
public class Assistance {

    @Id
    private String id;

    @DocumentReference
    private Users agentID;

    @DocumentReference
    private Users brokerID;

    @DocumentReference
    private Users prospectID;

    private AssistanceStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private Date createdAt;

    private Date desiredDate;
    private String description;
}
