package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.models.Notification;
import com.tawasalna.tawasalnacrisis.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private  NotificationRepository notificationRepository;

    public Notification createNotification(Incident incident) {
        Notification notification = new Notification();
        notification.setIncident(incident);
        notification.setDateCreation(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifs() {
        return notificationRepository.findAll();
    }

    @Override
    public void markAsRead(String id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
