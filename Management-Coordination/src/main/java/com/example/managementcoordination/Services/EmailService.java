package com.example.managementcoordination.Services;


import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;



    public void sendNewsletter(String subject, String content, List<String> recipients) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();  // Use this method
        javaMailSender.send(mimeMessage); // No need for casting
    }

    private MimeMessage createMimeMessage(String subject, String content, List<String> recipients) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setFrom(new InternetAddress("nahdi.amal@esprit.tn"));
            InternetAddress[] toAddresses = new InternetAddress[recipients.size()];
            for (int i = 0; i < recipients.size(); i++) {
                toAddresses[i] = new InternetAddress(recipients.get(i));
            }
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(content, "text/html");
            return mimeMessage;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void sendDailyNewsletter() {
        List<String> subscribers = getNewsletterSubscribers(); // Implement logic here (if simple)
        String content = generateNewsletterContent(); // Implement logic here (if simple)
        sendNewsletter("Your Daily Newsletter", content, subscribers);
    }

    private List<String> getNewsletterSubscribers() {
        // Implement logic to fetch subscribers (replace with your logic)
        return List.of("subscriber1@example.com", "subscriber2@example.com");
    }

    private String generateNewsletterContent() {
        // Implement logic to generate content (replace with your logic)
        return "<h1>This is your daily newsletter content!</h1>";
    }
}
