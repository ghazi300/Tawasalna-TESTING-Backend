package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.models.File;
import com.ipactconsult.tawasalnabackendapp.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@CrossOrigin("*")

public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File fileModel = fileService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + fileModel.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        File fileModel = fileService.getFile(id);
        if (fileModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Resource resource = fileService.loadFileAsResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileModel.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileModel.getFileName() + "\"")
                .body(resource);
    }
}
