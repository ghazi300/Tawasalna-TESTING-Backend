package com.tawasalna.social.repository;

import com.tawasalna.social.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId_ChatId(String chatId);

}