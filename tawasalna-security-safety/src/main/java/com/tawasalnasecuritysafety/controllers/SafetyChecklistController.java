package com.tawasalnasecuritysafety.controllers;

import com.tawasalnasecuritysafety.models.SafetyChecklist;
import com.tawasalnasecuritysafety.services.SafetyChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/safetychecklists")
@CrossOrigin("*")

public class SafetyChecklistController {
    @Autowired
    private SafetyChecklistService safetyChecklistService;

    @GetMapping
    public List<SafetyChecklist> getAllSafetyChecklists() {
        return safetyChecklistService.getAllSafetyChecklists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SafetyChecklist> getSafetyChecklistById(@PathVariable String id) {
        SafetyChecklist safetyChecklist = safetyChecklistService.getSafetyChecklistById(id).orElseThrow(() -> new RuntimeException("Safety Checklist not found"));
        return ResponseEntity.ok(safetyChecklist);
    }

    @PostMapping
    public SafetyChecklist createSafetyChecklist(@RequestBody SafetyChecklist safetyChecklist) {
        return safetyChecklistService.createSafetyChecklist(safetyChecklist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SafetyChecklist> updateSafetyChecklist(@PathVariable String id, @RequestBody SafetyChecklist safetyChecklistDetails) {
        SafetyChecklist updatedSafetyChecklist = safetyChecklistService.updateSafetyChecklist(id, safetyChecklistDetails);
        return ResponseEntity.ok(updatedSafetyChecklist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSafetyChecklist(@PathVariable String id) {
        safetyChecklistService.deleteSafetyChecklist(id);
        return ResponseEntity.noContent().build();
    }
}
