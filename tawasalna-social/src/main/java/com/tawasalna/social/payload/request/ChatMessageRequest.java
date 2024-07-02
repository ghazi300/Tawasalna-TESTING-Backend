package com.tawasalna.social.payload.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data

public class ChatMessageRequest {

    private String id;
    private String chatId;

    private String sender;

    private String recipient;
    private String content;
    private String timestamp; // Store timestamp as string



}