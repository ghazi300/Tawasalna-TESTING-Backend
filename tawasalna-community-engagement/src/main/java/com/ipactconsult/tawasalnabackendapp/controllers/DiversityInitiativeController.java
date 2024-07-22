package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.InitiativeRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.CulturalResponse;
import com.ipactconsult.tawasalnabackendapp.payload.response.InitiativeResponse;
import com.ipactconsult.tawasalnabackendapp.service.IDiversityInitiativeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("diversity")
@CrossOrigin("*")

public class DiversityInitiativeController {
    private final IDiversityInitiativeService service;
    @PostMapping
    public ResponseEntity<String> createInitiative(@Valid @RequestBody InitiativeRequest initiativeRequest) {
        return ResponseEntity.ok(service.save(initiativeRequest));
    }
    @GetMapping
    public ResponseEntity<List<InitiativeResponse>> getAllInitiatives() {
        List<InitiativeResponse> initiativeResponseList = service.getAllInitiatives();
        return ResponseEntity.ok(initiativeResponseList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInitiative(@PathVariable String id) {
        service.deleteInitiative(id);
        return ResponseEntity.noContent().build();
    }
}
