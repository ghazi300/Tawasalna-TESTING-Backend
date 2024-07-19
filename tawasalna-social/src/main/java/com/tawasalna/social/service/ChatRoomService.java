package com.tawasalna.social.service;

import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.models.ChatRoom;
import com.tawasalna.social.repository.ChatRoomRepository;
import com.tawasalna.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements IChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userConsumerService;


    @Override
    public Optional<String> getChatRoomId(
            String user1Id,
            String user2Id,
            boolean createNewRoomIfNotExists
    ) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (chatRoom.isPresent()) {
            return chatRoom.map(ChatRoom::getChatId);
        } else {
            chatRoom = chatRoomRepository.findByUser1IdAndUser2Id(user2Id, user1Id);
            if (chatRoom.isPresent()) {
                return chatRoom.map(ChatRoom::getChatId);
            } else {
                if (createNewRoomIfNotExists) {
                    String chatId = createChatId(user1Id, user2Id);
                    return Optional.of(chatId);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public String createChatId(String user1Id, String user2Id) {
        var chatId = String.format("%s_%s", user1Id, user2Id);

        final Users sender = userConsumerService.findById(user1Id)
                .orElseThrow(() -> new InvalidUserException(user1Id, "User not found"));
        final Users recipient = userConsumerService.findById(user2Id)
                .orElseThrow(() -> new InvalidUserException(user2Id, "User not found"));

        ChatRoom room = ChatRoom.builder()
                .chatId(chatId)
                .user1(sender)
                .user2(recipient)
                .build();


        chatRoomRepository.save(room);


        return chatId;
    }

    @Override
    public ChatRoom getChatRoomById(String roomId) {
        return chatRoomRepository.findByChatId(roomId).orElse(null);
    }

    @Override
    public List<ChatRoom> getUserChatRooms(String userId) {
        // Retrieve chat rooms where the specified user is either the sender or the recipient
        return chatRoomRepository.findByUser1IdOrUser2Id(userId, userId);
    }

 }