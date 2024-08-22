package com.example.residentsupportservices.Controlleur;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private static final String UPLOAD_DIR = "G:\\Esprit4TWIN4\\stage\\src\\assets\\images";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        // Validate file
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        try {
            // Ensure the upload directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Create a path to the file
            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            // Copy the file to the target location (replacing existing file with the same name)
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Construct the file path to return
            String fileUrl = "/assets/images/" + file.getOriginalFilename();
            return ResponseEntity.ok().body(Collections.singletonMap("filePath", fileUrl));
        } catch (IOException e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }
}
