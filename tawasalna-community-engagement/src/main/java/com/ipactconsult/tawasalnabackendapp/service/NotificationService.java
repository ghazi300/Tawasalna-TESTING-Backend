package com.ipactconsult.tawasalnabackendapp.service;


import com.ipactconsult.tawasalnabackendapp.models.Notification;
import com.ipactconsult.tawasalnabackendapp.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor

public class NotificationService {
    private final NotificationRepository notificationRepository;
    public Notification saveNotification(String message) {

        Notification savedNotification=Notification.builder()
                .message(message)
                .build();
        return notificationRepository.save(savedNotification);
    }
    public long getNotificationCount() {
        return notificationRepository.count();
    }

}
