package com.example.managementcoordination.Services;


import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private AtomicInteger emailCount = new AtomicInteger(0);
    private List<Date> emailTimestamps = new ArrayList<>();
    private AtomicInteger responseCount = new AtomicInteger(0);


    public void sendNewsletter(String subject, String content, List<String> recipients , MultipartFile pdf) {
        MimeMessage mimeMessage = createMimeMessage(subject, content, recipients , pdf);
        javaMailSender.send(mimeMessage);
        emailCount.incrementAndGet();
        emailTimestamps.add(new Date());

    }

    private MimeMessage createMimeMessage(String subject, String content, List<String> recipients, MultipartFile pdf) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true indicates multipart
            helper.setFrom(new InternetAddress("nahdii13@gmail.com"));
            InternetAddress[] toAddresses = new InternetAddress[recipients.size()];
            for (int i = 0; i < recipients.size(); i++) {
                toAddresses[i] = new InternetAddress(recipients.get(i));
            }
            helper.setTo(toAddresses);
            helper.setSubject(subject);
            helper.setText(content, true); // true indicates HTML content

            if (pdf != null && !pdf.isEmpty()) {
                helper.addAttachment(pdf.getOriginalFilename(), pdf);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return mimeMessage;
    }


    public List<String> getNewsletterSubscribers() {
        // Static list of subscribers
        return List.of(
                "nahdii13@gmail.com",
                "nahdii13@gmail.com"
        );
    }

    public String generateNewsletterContent() {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body {font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;}" +
                ".container {max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border: 1px solid #dddddd;}" +
                ".header {background-color: #4a148c; color: white; padding: 10px 0; text-align: center;}" +
                ".content {padding: 20px;}" +
                ".footer {background-color: #6a1b9a; color: white; text-align: center; padding: 20px 0; font-size: 14px;}" +
                ".button {background-color: #4a148c; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>Tawasalna Skyline Highlights</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<h2>Welcome to Our Newsletter</h2>" +
                "<p>Hello,</p>" +
                "<p>Experience the vibrant essence of Dubai, where modern architecture meets traditional charm.</p>" +
                "<h3>Featured Locations</h3>" +
                "<p>Explore the iconic Burj Khalifa, the stunning Dubai Marina, and the luxurious Palm Jumeirah. Discover what makes Dubai a city of dreams.</p>" +
                "<h3>Upcoming Events</h3>" +
                "<p>Join us for exclusive events showcasing Dubai's cultural richness and innovation.</p>" +
                "<a href='https://example.com' class='button'>Learn More</a>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>&copy; 2024 Dubai Attractions. All rights reserved.</p>" +
                "<p><a href='https://example.com/unsubscribe' style='color: white; text-decoration: underline;'>Unsubscribe</a> from this newsletter</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public int getTotalEmailsSent() {
        return emailCount.get();
    }


    public int getTotalEmailsSentInTimeFrame(Date start, Date end) {
        return (int) emailTimestamps.stream()
                .filter(timestamp -> !timestamp.before(start) && !timestamp.after(end))
                .count();
    }
    public void recordResponse() {
        responseCount.incrementAndGet();
    }
    public int getTotalResponses() {
        return responseCount.get();
    }
    public double getResponsePercentage() {
        int totalEmails = emailCount.get();
        int totalResponses = responseCount.get();
        if (totalEmails == 0) return 0.0;
        return (totalResponses / (double) totalEmails) * 100;
    }
}
