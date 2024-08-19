package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.File;
import com.tawasalna.tawasalnacrisis.services.FileService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<List<File>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            List<File> attachments = fileService.saveAttachments(files);
            return ResponseEntity.ok(attachments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable String id) {
        System.out.println("Fetching file with ID: " + id); // Ajoutez un log pour vérifier l'ID
        Optional<File> fileOptional = fileService.getFileById(id);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            return ResponseEntity.ok()
                    .header("Content-Type", file.getFileType())
                    .header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getData());
        } else {
            System.out.println("File not found with ID: " + id); // Ajoutez un log pour les cas où le fichier n'est pas trouvé
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<File>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }
}
