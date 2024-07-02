package com.tawasalna.social.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tawasalna.shared.userapi.model.Users;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class ResidentComments {
    @Id
    private String id;
    @JsonBackReference
    @DocumentReference
    private ResidentPost post;
    @DocumentReference
    private Users user;
    private String text;
    private Date createdAt;
    private List<Reply> replies;

}
