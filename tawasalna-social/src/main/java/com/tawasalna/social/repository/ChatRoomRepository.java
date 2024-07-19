package com.tawasalna.social.repository;

import com.tawasalna.social.models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findByUser1IdAndUser2Id(String senderId, String recipientId);
    List<ChatRoom> findByUser1IdOrUser2Id(String userId, String userId1);
    Optional<ChatRoom> findByChatId(String roomId);
}