package com.tawasalna.social.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tawasalna.shared.userapi.model.Users;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "residentPosts")
public class ResidentPost {

    @Id
    private String id;

    @DocumentReference
    private Users user;
    private String caption;
    private List<String> photos = new ArrayList<>();
    private String video;
    private Date postDateTime;
    private Set<String> likedBy = new HashSet<>();

    @JsonManagedReference
    @DocumentReference
    private List<ResidentComments> comments = new ArrayList<>();
    private String businessId;
}
