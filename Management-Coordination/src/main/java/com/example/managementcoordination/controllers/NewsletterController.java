package com.example.managementcoordination.controllers;

import com.example.managementcoordination.Services.EmailService;
import com.example.managementcoordination.Services.NewsletterService;
import com.example.managementcoordination.entities.Newsletter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/newsletters")
@AllArgsConstructor
public class NewsletterController {
    private NewsletterService newsletterService;

    //private static final String UPLOAD_DIRECTORY = "C:/Users/amal/Desktop/exemple/tawasolna-backend-app-develop/Management-Coordination/Newsletter";

    //@PostMapping
   // public ResponseEntity<Newsletter> createNewsletter(
           /* @RequestBody Newsletter newsletter,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String filePath = saveFile(file);
            newsletter.setFilePath(filePath); // Assuming a filePath field in Newsletter
            Newsletter createdNewsletter = newsletterService.createNewsletter(newsletter);
            return new ResponseEntity<>(createdNewsletter, HttpStatus.CREATED);
        } catch (IOException e) {
            // Handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    /*private String saveFile(MultipartFile file) throws IOException {
        // Generate a unique filename
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIRECTORY, filename);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        return filePath.toString();
    }*/
    @PostMapping
    public ResponseEntity<Newsletter> createNewsletter(@RequestParam("file") MultipartFile file, @RequestBody Newsletter newsletter) throws IOException {
        Newsletter createdNewsletter = newsletterService.createNewsletter(file, newsletter);
        return new ResponseEntity<>(createdNewsletter, HttpStatus.CREATED);
    }

   /* @GetMapping("/loadFile/{fileName}")
    public ResponseEntity<Resource> loadFile(@PathVariable String fileName) {
        Resource resource = NewsletterService.loadFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }*/

    @GetMapping
    public ResponseEntity<List<Newsletter>> getAllNewsletters() {
        List<Newsletter> newsletters = newsletterService.getAllNewsletters();
        return new ResponseEntity<>(newsletters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Newsletter> getNewsletterById(@PathVariable String id) {
        Newsletter newsletter = newsletterService.getNewsletterById(id);
        if (newsletter != null) {
            return new ResponseEntity<>(newsletter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Newsletter> updateNewsletter(@PathVariable String id, @RequestBody Newsletter newsletter) {
        Newsletter updatedNewsletter = newsletterService.updateNewsletter(newsletter);
        return new ResponseEntity<>(updatedNewsletter, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsletter(@PathVariable String id) {
        newsletterService.deleteNewsletter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
/*
    //email-sending
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-newsletter")
    public String sendNewsletter(@RequestBody Map<String, Object> payload) {
        String subject = (String) payload.get("subject");
        String content = (String) payload.get("content");
        List<String> recipients = (List<String>) payload.get("recipients");

        emailService.sendNewsletter(subject, content, recipients);
        return "Email sent successfully";
    }

    @PostMapping("/send-daily-newsletter")
    public String sendDailyNewsletter() {
        List<String> subscribers = emailService.getNewsletterSubscribers();
        String content = emailService.generateNewsletterContent();
        emailService.sendNewsletter("Your Daily Newsletter", content, subscribers);
        return "Daily newsletter sent successfully";
    }
*/
}
