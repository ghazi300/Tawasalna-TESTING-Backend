package com.example.managementcoordination.controllers;

import com.example.managementcoordination.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-newsletter")
    public String sendNewsletter(
            @RequestParam("subject") String subject,
            @RequestParam("header") String header,
            @RequestParam("content") String content,
            @RequestParam("recipients") String recipients, // Accept as a comma-separated string
            @RequestParam(value = "pdf", required = false) MultipartFile pdf) {

        List<String> recipientList = List.of(recipients.split("\\s*,\\s*"));
        emailService.sendNewsletter(subject, content, recipientList, pdf);
        return "Email sent successfully";
    }

    @PostMapping("/send-daily-newsletter")
    public String sendDailyNewsletter() {
        List<String> subscribers = emailService.getNewsletterSubscribers();
        String content = emailService.generateNewsletterContent();
        emailService.sendNewsletter("Your Daily Newsletter", content, subscribers, null);
        return "Daily newsletter sent successfully";
    }

    @GetMapping("/subscribers")
    public List<String> getSubscribers() {
        return emailService.getNewsletterSubscribers();
    }

    @GetMapping("/total-emails-sent")
    public int getTotalEmailsSent() {
        return emailService.getTotalEmailsSent();
    }

    @GetMapping("/total-emails-sent-in-timeframe")
    public ResponseEntity<Integer> getTotalEmailsSentInTimeFrame(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        Date startDate = Date.from(start.toInstant(ZoneOffset.UTC));
        Date endDate = Date.from(end.toInstant(ZoneOffset.UTC));
        return ResponseEntity.ok(emailService.getTotalEmailsSentInTimeFrame(startDate, endDate));
    }

    // Updated endpoint to record response and update in database
    @PostMapping("/record-response")
    public ResponseEntity<Void> recordResponse(@RequestParam("newsletterId") String newsletterId) {
        emailService.recordResponse(newsletterId);  // Pass the newsletterId to update response count
        return ResponseEntity.ok().build();
    }

    // Updated endpoint to get total responses for a specific newsletter
    @GetMapping("/total-responses")
    public ResponseEntity<Integer> getTotalResponses(@RequestParam("newsletterId") String newsletterId) {
        int totalResponses = emailService.getTotalResponses(newsletterId);  // Fetch total responses from DB
        return ResponseEntity.ok(totalResponses);
    }

    @GetMapping("/response-percentage")
    public ResponseEntity<Double> getResponsePercentage() {
        double responsePercentage = emailService.getResponsePercentage();
        return ResponseEntity.ok(responsePercentage);
    }
}
