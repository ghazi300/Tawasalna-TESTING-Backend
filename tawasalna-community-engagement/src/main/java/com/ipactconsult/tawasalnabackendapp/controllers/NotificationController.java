package com.ipactconsult.tawasalnabackendapp.controllers;


import com.ipactconsult.tawasalnabackendapp.payload.request.Message;
import com.ipactconsult.tawasalnabackendapp.service.NotificationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller


@CrossOrigin("*")

public class NotificationController {
    private int notificationCount=0 ;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    NotificationService notificationService;

   


    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Message send(final Message message) throws Exception {
        incrementNotificationCount();
        System.out.println(message);
        notificationService.saveNotification(message.getText());
        return message;
    }
    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
    private void incrementNotificationCount() {
        notificationCount++;
        // Broadcast notification count update to all subscribed clients
        simpMessagingTemplate.convertAndSend("/topic/notificationCount", notificationCount);
    }
    @GetMapping("/count")
    public ResponseEntity<Integer> getNotificationCount() {
        return ResponseEntity.ok(notificationCount);
    }



}
