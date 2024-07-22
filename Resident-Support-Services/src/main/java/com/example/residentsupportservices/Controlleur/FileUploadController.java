package com.example.residentsupportservices.Controlleur;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.nio.file.*;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private static final String UPLOAD_DIR = "C:\\Users\\abidf\\Tawasalna-PMS\\src\\assets\\images";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path copyLocation = Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            String filePath = "/assets/images/" + file.getOriginalFilename();
            return ResponseEntity.ok().body(Collections.singletonMap("filePath", filePath));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }
}
