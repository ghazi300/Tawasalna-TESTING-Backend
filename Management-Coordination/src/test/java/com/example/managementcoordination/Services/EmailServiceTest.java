package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Newsletter;
import com.example.managementcoordination.repositories.NewsletterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private NewsletterRepository newsletterRepository;

    @InjectMocks
    private EmailService emailService;

    private List<String> recipients;

    @BeforeEach
    void setUp() {
        recipients = Arrays.asList("recipient1@example.com", "recipient2@example.com");
    }

    @Test
    void sendNewsletterWithoutFile() {
        }

    @Test
    void getNewsletterSubscribers() {
        List<String> subscribers = emailService.getNewsletterSubscribers();
        assertEquals(2, subscribers.size());
    }

    @Test
    void generateNewsletterContent() {
        String content = emailService.generateNewsletterContent();
        assertEquals(true, content.contains("Tawasalna Skyline Highlights"));
    }



    @Test
    void getTotalEmailsSentInTimeFrame() {
    }
}
