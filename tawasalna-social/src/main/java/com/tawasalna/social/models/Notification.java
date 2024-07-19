package com.tawasalna.social.models;

import com.tawasalna.shared.userapi.model.Users;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    @DocumentReference
    @Indexed
    private Users recipientUser;
    @DocumentReference
    private Users senderUser;
    private String postId;
    private String message;
    private String type;
    private Date createdAt;
    private boolean isRead;
}
