package com.tawasalna.social.config;

import com.tawasalna.shared.userapi.model.ResidentProfile;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.models.Notification;
import com.tawasalna.social.repository.NotificationRepository;
import com.tawasalna.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
public class MyWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ConcurrentMap<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        String userId = session.getUri().getQuery().split("=")[1];
        userSessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        if (payload.startsWith("followResponse:")) {
            handleFollowResponse(payload);
        } else {
            for (WebSocketSession webSocketSession : sessions) {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage("Echo: " + payload));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        userSessions.values().remove(session);
    }

    public void sendFollowRequestNotification(String userId, String message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFollowNotification(String userId, String message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendLikeNotification(String userId, String message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCommentNotification(String userId, String message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendReplyNotification(String userId, String message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMentionNotification(String userId, String message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleFollowResponse(String payload) {
        String[] parts = payload.split(":");
        String userIdToFollow = parts[1];
        String followerUserId = parts[2];
        String response = parts[3];

        System.out.println("userIdToFollow: " + userIdToFollow);
        System.out.println("followerUserId: " + followerUserId);

        Users usertofollow = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));
        Users followeruser = userRepository.findById(followerUserId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if ("yes".equalsIgnoreCase(response)) {
            ResidentProfile followerProfile = followeruser.getResidentProfile();
            ResidentProfile userToFollowProfile = usertofollow.getResidentProfile();

            if (followerProfile.getFollowing() == null) {
                followerProfile.setFollowing(new ArrayList<>());
            }
            if (!followerProfile.getFollowing().contains(userIdToFollow)) {
                followerProfile.getFollowing().add(userIdToFollow);
            }

            if (userToFollowProfile.getFollowers() == null) {
                userToFollowProfile.setFollowers(new ArrayList<>());
            }
            if (!userToFollowProfile.getFollowers().contains(followerUserId)) {
                userToFollowProfile.getFollowers().add(followerUserId);
            }

            userRepository.save(followeruser);
            userRepository.save(usertofollow);

            sendFollowNotification(followerUserId, "Your follow request to user " + usertofollow.getResidentProfile().getFullName() + " has been accepted.");

            Notification notification = new Notification();
            notification.setRecipientUser(followeruser);
            notification.setSenderUser(usertofollow);
            notification.setMessage("Your follow request to user " + usertofollow.getResidentProfile().getFullName() + " has been accepted.");
            notification.setType("acceptedFollowNotification");
            notification.setCreatedAt(new Date());
            notification.setRead(false);
            notificationRepository.save(notification);
            followeruser.getResidentProfile().getNotifications().add(notification.getId());
            userRepository.save(followeruser);

        } else if ("no".equalsIgnoreCase(response)) {
            sendFollowNotification(followerUserId, "Your follow request to user " + usertofollow.getResidentProfile().getFullName() + " has been declined.");

            Notification notification = new Notification();
            notification.setRecipientUser(followeruser);
            notification.setSenderUser(usertofollow);
            notification.setMessage("Your follow request to user " + usertofollow.getResidentProfile().getFullName() + " has been declined.");
            notification.setType("acceptedFollowNotification");
            notification.setCreatedAt(new Date());
            notification.setRead(false);
            notificationRepository.save(notification);
            followeruser.getResidentProfile().getNotifications().add(notification.getId());
            userRepository.save(followeruser);
        }
    }
}
