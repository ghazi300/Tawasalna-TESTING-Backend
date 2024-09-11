package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Notification;
import com.tawasalna.tawasalnacrisis.services.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendNotification() {
        Notification notification = new Notification();
        Notification result = notificationController.sendNotification(notification);
        assertEquals(notification, result);
    }

    @Test
    public void testNotifyFrontend() {
        Notification notification = new Notification();
        notificationController.notifyFrontend(notification);
        verify(messagingTemplate, times(1)).convertAndSend("/topic/notifications", notification);
    }

    @Test
    public void testGetAllNotifications() {
        Notification notification1 = new Notification();
        Notification notification2 = new Notification();
        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getAllNotifs()).thenReturn(notifications);

        List<Notification> result = notificationController.getAllNotifications();

        assertEquals(2, result.size());
        verify(notificationService, times(1)).getAllNotifs();
    }

    @Test
    public void testMarkAsRead() {
        String id = "test-id";
        ResponseEntity<Void> response = notificationController.markAsRead(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(notificationService, times(1)).markAsRead(id);
    }
}
