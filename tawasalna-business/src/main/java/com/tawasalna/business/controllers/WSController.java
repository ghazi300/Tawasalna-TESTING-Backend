package com.tawasalna.business.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {

    @MessageMapping("/business")
    @SendTo("/topic/messages")
    public String send(String message) {
        return "test" + message;
    }
}
