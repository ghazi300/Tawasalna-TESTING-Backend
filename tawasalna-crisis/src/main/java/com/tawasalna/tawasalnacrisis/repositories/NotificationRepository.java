package com.tawasalna.tawasalnacrisis.repositories;


import com.tawasalna.tawasalnacrisis.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}

