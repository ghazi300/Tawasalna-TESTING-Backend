package com.tawasalna.social.repository;

import com.tawasalna.social.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
    @Query(value = "{ 'recipientUser.id': ?0 }")
    List<Notification> findByRecipientUserId(String userId);}
