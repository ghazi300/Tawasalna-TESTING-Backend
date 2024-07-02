package com.tawasalna.social.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String id;
    private String recipientUserId;
    private String senderUserId;
    private String senderFullName;
    private String postId;
    private String message;
    private String type;
    private Date createdAt;
    private boolean isRead;
}
