package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.File;
import com.tawasalna.tawasalnacrisis.services.FileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/files")
@CrossOrigin("*")
public class FileController {
    private FileService fileService;
    @PostMapping("/upload")
    public ResponseEntity<List<File>> uploadMultipleFiles(@Valid @RequestParam("files") MultipartFile[] files) {
        try {
            List<File> attachments = fileService.saveAttachments(files);
            return ResponseEntity.ok(attachments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getFileByName(@PathVariable String filename) {
        Optional<File> fileOptional = fileService.getFileByName(filename);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, file.getFileType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}