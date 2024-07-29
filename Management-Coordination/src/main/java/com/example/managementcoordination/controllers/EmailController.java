package com.example.managementcoordination.controllers;

import com.example.managementcoordination.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:4200")


public class EmailController {
    @Autowired
    private EmailService emailService;

   /*@PostMapping("/send-newsletter")
    public String sendNewsletter(@RequestBody Map<String, Object> payload) {
        String subject = (String) payload.get("subject");
        String content = (String) payload.get("content");
        List<String> recipients = (List<String>) payload.get("recipients");

        emailService.sendNewsletter(subject, content, recipients);
        return "Email sent successfully";
    }*/
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

   /* @PostMapping("/send-daily-newsletter")
    public String sendDailyNewsletter() {
        List<String> subscribers = emailService.getNewsletterSubscribers();
        String content = emailService.generateNewsletterContent();
        emailService.sendNewsletter("Your Daily Newsletter", content, subscribers);
        return "Daily newsletter sent successfully";
    }
    @GetMapping("/subscribers")
    public List<String> getSubscribers() {
        return emailService.getNewsletterSubscribers();
    }*/
   @PostMapping("/send-daily-newsletter")
   public String sendDailyNewsletter() {
       List<String> subscribers = emailService.getNewsletterSubscribers();
       String content = emailService.generateNewsletterContent();
       emailService.sendNewsletter("Your Daily Newsletter", content, subscribers, null);
       return "Daily newsletter sent successfully";
   }

}

