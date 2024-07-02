package com.tawasalna.tawasalnacrm.controller;

import com.tawasalna.tawasalnacrm.models.Logo;
import com.tawasalna.tawasalnacrm.service.LogoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crm/logos")
@AllArgsConstructor
public class LogoController {

    private final LogoService logoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadLogo(@RequestParam("file") MultipartFile file) {
        try {
            Logo savedLogo = logoService.saveLogo(file);
            return ResponseEntity.ok(savedLogo);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{logoId}")
    public ResponseEntity<?> deleteLogo(@PathVariable String logoId) {
        logoService.deleteLogo(logoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{logoId}")
    public ResponseEntity<?> getLogo(@PathVariable String logoId) {
        Optional<Logo> logoOptional = logoService.getLogo(logoId);
        if (logoOptional.isPresent()) {
            return ResponseEntity.ok(logoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLogos() {
        List<Logo> logos = logoService.getAllLogos();
        return ResponseEntity.ok(logos);
    }

    @PutMapping("/{logoId}")
    public ResponseEntity<?> updateLogo(@PathVariable String logoId, @RequestParam("file") MultipartFile file) {
        try {
            Logo updatedLogo = logoService.updateLogo(logoId, file);
            return ResponseEntity.ok(updatedLogo);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{logoId}/archive")
    public ResponseEntity<?> archiveLogo(@PathVariable String logoId) {
        logoService.archiveLogo(logoId);
        return ResponseEntity.ok().build();
    }
}
