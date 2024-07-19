package com.tawasalna.social.service;


import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.models.ChatMessage;
import com.tawasalna.social.models.ChatRoom;
import com.tawasalna.social.payload.request.ChatMessageRequest;
import com.tawasalna.social.repository.ChatMessageRepository;
import com.tawasalna.social.repository.ChatRoomRepository;
import com.tawasalna.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageService implements IChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomRepository repositoryChatRoom;
    private final ChatRoomService chatRoomService;
    private final UserRepository userConsumerService;

    @Override
    public ChatMessage save(ChatMessageRequest chatMessageRequest) {
        // Retrieve sender and recipient objects from UserConsumerService
        Users sender = userConsumerService.findById(chatMessageRequest.getSender())
                .orElseThrow(() -> new InvalidUserException(chatMessageRequest.getSender(), "Sender not found"));
        Users recipient = userConsumerService.findById(chatMessageRequest.getRecipient())
                .orElseThrow(() -> new InvalidUserException(chatMessageRequest.getRecipient(), "Recipient not found"));

        // Create a new ChatMessage object
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(chatMessageRequest.getId());

        chatMessage.setSender(sender);
        chatMessage.setRecipient(recipient);
        chatMessage.setContent(chatMessageRequest.getContent());
        LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("Europe/Paris"));


        chatMessage.setTimestamp(timestamp);

        // Set chatId
        String chatId = chatRoomService.getChatRoomId(sender.getId(), recipient.getId(), true)
                .orElseThrow();

        Optional<ChatRoom> chatRoomOptional = repositoryChatRoom.findByChatId(chatId);
        chatRoomOptional.ifPresent(chatMessage::setChatId);

        // Save and return the ChatMessage
        return repository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId_ChatId).orElse(new ArrayList<>());
    }

    @Override
    public List<ChatMessage> findChatMessagesByRoomId(String roomId) {
        return repository.findByChatId_ChatId(roomId);
    }
}
