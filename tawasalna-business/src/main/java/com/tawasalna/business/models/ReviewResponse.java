package com.tawasalna.business.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tawasalna.business.models.enums.ReviewMode;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewResponse {

    @Id
    private String id;

    private String content;

    @DocumentReference
    private Users responder;

    @JsonIgnore
    @DocumentReference
    private Review review;

    private ReviewMode mode;
}
