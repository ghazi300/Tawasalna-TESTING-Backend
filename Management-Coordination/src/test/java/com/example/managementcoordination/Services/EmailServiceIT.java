package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Newsletter;
import com.example.managementcoordination.repositories.NewsletterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmailServiceIT {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private NewsletterRepository newsletterRepository;

    @InjectMocks
    private EmailService emailService;

    private Newsletter testNewsletter;

    @BeforeEach
    void setUp() {
        testNewsletter = new Newsletter(
                "1",
                "Monthly Update",
                "Here is the content of the newsletter...",
                Arrays.asList("user1@example.com", "user2@example.com"),
                new Date(),
                "/path/to/file",
                10
        );
    }



    @Test
    void getNewsletterSubscribers() {
        List<String> subscribers = emailService.getNewsletterSubscribers();

        assertNotNull(subscribers, "Subscribers list should not be null.");
        assertTrue(subscribers.contains("nahdii13@gmail.com"), "Subscribers list should contain the test email.");
    }

    @Test
    void generateNewsletterContent() {
        String content = emailService.generateNewsletterContent();

        assertNotNull(content, "Generated content should not be null.");
        assertTrue(content.contains("Tawasalna Skyline Highlights"), "Content should include the header.");
        assertTrue(content.contains("Welcome to Our Newsletter"), "Content should include the welcome message.");
        assertTrue(content.contains("Experience the vibrant essence of Dubai"), "Content should describe Dubai.");
    }





}
