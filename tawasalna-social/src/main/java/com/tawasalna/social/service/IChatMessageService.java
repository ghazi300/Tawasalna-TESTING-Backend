package com.tawasalna.social.service;

import com.tawasalna.social.models.ChatMessage;
import com.tawasalna.social.payload.request.ChatMessageRequest;

import java.util.List;

public interface IChatMessageService {
    ChatMessage save(ChatMessageRequest chatMessage);

    List<ChatMessage> findChatMessages(String senderId, String recipientId);

    List<ChatMessage> findChatMessagesByRoomId(String roomId);
}
