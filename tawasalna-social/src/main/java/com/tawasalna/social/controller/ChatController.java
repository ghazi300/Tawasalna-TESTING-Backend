package com.tawasalna.social.controller;

import com.tawasalna.social.models.ChatMessage;
import com.tawasalna.social.models.ChatNotification;
import com.tawasalna.social.models.ChatRoom;
import com.tawasalna.social.payload.TypingIndicator;
import com.tawasalna.social.payload.request.ChatMessageRequest;
import com.tawasalna.social.service.IChatMessageService;
import com.tawasalna.social.service.IChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IChatMessageService chatMessageService;
    private final IChatRoomService chatRoomService;

    @MessageMapping("/typing")
    public void typingIndicator(@Payload TypingIndicator typingIndicator) {
        String recipient = typingIndicator.getRecipient();
        String chatId = typingIndicator.getChatId();
        String destination = "/queue/typing/" + chatId;
        messagingTemplate.convertAndSendToUser(recipient, destination, "typing");
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequest chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        String recipient = chatMessage.getRecipient();
        String chatId = chatMessage.getChatId();
        String destination = "/queue/messages/" + chatId;
        System.out.println("Sending to destination: " + destination);
        messagingTemplate.convertAndSendToUser(
                recipient, destination,
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSender(),
                        savedMsg.getRecipient(),
                        savedMsg.getContent(),
                        savedMsg.getTimestamp().toString()
                )
        );

        String notificationDestination = "/user/" + recipient + "/queue/notifications";
        System.out.println("Sending notification to destination: " + notificationDestination);
        messagingTemplate.convertAndSend(notificationDestination, new ChatNotification(
                savedMsg.getId(),
                savedMsg.getSender(),
                savedMsg.getRecipient(),
                savedMsg.getContent(),
                savedMsg.getTimestamp().toString()
        ));
    }

    @SubscribeMapping("/room/{roomId}")
    public List<ChatMessage> subscribeToRoom(@DestinationVariable String roomId) {

        return chatMessageService.findChatMessagesByRoomId(roomId);
    }

    @GetMapping("/messages/{roomid}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable String roomid) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessagesByRoomId(roomid));
    }

    @GetMapping("/chat-room/{senderId}/{recipientId}")
    public ResponseEntity<String> getChatRoomId(@PathVariable String senderId,
                                                @PathVariable String recipientId) {
        String chatRoomId = chatRoomService.getChatRoomId(senderId, recipientId, true)
                .orElseThrow(() -> new RuntimeException("Failed to get chat room ID"));
        return ResponseEntity.ok(chatRoomId);
    }

    @GetMapping("/chatRooms/{roomId}")
    public ResponseEntity<ChatRoom> getChatRoomById(@PathVariable String roomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(roomId);
        if (chatRoom != null) {
            return new ResponseEntity<>(chatRoom, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/myrooms/{userId}")
    public ResponseEntity<List<ChatRoom>> getUserChatRooms(@PathVariable String userId) {
        List<ChatRoom> chatRooms = chatRoomService.getUserChatRooms(userId);
        return ResponseEntity.ok(chatRooms);
    }
}
