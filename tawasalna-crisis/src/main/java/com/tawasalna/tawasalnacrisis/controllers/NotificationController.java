package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Notification;
import com.tawasalna.tawasalnacrisis.services.NotificationService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/notif")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {
    private  SimpMessagingTemplate messagingTemplate;
    private NotificationService notificationService;
    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Notification sendNotification(Notification notification) {
        return notification;
    }

    public void notifyFrontend(Notification notification) {
        this.messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
    @GetMapping
    public List<Notification> getAllNotifications(){
        return notificationService.getAllNotifs();
    }
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable String id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
