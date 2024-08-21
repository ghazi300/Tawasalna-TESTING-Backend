package com.tawasalna.tawasalnacrisis.services;

import com.tawasalna.tawasalnacrisis.models.Incident;
import com.tawasalna.tawasalnacrisis.models.Notification;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Incident incident);
    List<Notification> getAllNotifs();
    void markAsRead(String id);
}
