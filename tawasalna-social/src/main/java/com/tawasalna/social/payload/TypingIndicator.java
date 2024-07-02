package com.tawasalna.social.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TypingIndicator {
    private String recipient;
    private String chatId;

    // Constructor, getters, and setters
}

