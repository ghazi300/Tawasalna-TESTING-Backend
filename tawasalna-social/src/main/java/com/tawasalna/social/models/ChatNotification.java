package com.tawasalna.social.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private String id;
    @DocumentReference
    private Users sender;
    @DocumentReference
    private Users recipient;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;
}