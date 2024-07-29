package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Newsletter;
import com.example.managementcoordination.repositories.NewsletterRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NewsletterService {
    @Autowired
    private NewsletterRepository newsletterRepository ;
    public Newsletter createNewsletter(Newsletter newsletter) {
        return newsletterRepository.save(newsletter);
    }
    public void deleteNewsletter(String id) {
        newsletterRepository.deleteById(id);
    }

    public Newsletter updateNewsletter(Newsletter newsletter) {
        return newsletterRepository.save(newsletter);
    }

    public List<Newsletter> getAllNewsletters() {
        return newsletterRepository.findAll();
    }

    public Newsletter getNewsletterById(String id) {
        return newsletterRepository.findById(id).orElse(null);
    }

    private final Path root = Paths.get("src/main/resources/Newsletters");

    public Newsletter createNewsletter(MultipartFile file, Newsletter newsletter) throws IOException {
        try {
            // Save the uploaded PDF file
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = this.root.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // Set the file path in the newsletter object
            newsletter.setFilePath(filePath.toString());

            // Save the newsletter (including file path) to the database
            Newsletter savedNewsletter = newsletterRepository.save(newsletter);

            return savedNewsletter;
        } catch (IOException e) {
            throw new RuntimeException("Error saving PDF file or newsletter: " + e.getMessage());
        }
    }

    public Resource loadFile(String fileName) {
        try {
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
