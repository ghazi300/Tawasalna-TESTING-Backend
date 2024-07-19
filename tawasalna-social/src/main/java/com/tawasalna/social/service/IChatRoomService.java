package com.tawasalna.social.service;

import com.tawasalna.social.models.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface IChatRoomService {
    Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    );

    String createChatId(String senderId, String recipientId);

    ChatRoom getChatRoomById(String roomId);


    List<ChatRoom> getUserChatRooms(String userId);
}
