package com.tawasalna.business.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Getter
@Setter
public class Opportunity {

    @Id
    private String id;

    private String subject;

    private String details;

    private Boolean signed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateTime;

    @DocumentReference
    private Users publisher;

    @DocumentReference
    private List<Users> participants;

    @DocumentReference
    private Contract agreement;
}
