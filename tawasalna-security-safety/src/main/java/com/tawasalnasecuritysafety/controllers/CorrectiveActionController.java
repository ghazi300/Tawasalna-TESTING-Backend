package com.tawasalnasecuritysafety.controllers;

import com.tawasalnasecuritysafety.models.CorrectiveAction;
import com.tawasalnasecuritysafety.services.CorrectiveActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/correctiveactions")
@CrossOrigin("*")
public class CorrectiveActionController {
    @Autowired
    private CorrectiveActionService correctiveActionService;

    @GetMapping
    public List<CorrectiveAction> getAllCorrectiveActions() {
        return correctiveActionService.getAllCorrectiveActions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorrectiveAction> getCorrectiveActionById(@PathVariable String id) {
        CorrectiveAction correctiveAction = correctiveActionService.getCorrectiveActionById(id).orElseThrow(() -> new RuntimeException("Corrective Action not found"));
        return ResponseEntity.ok(correctiveAction);
    }

    @PostMapping
    public CorrectiveAction createCorrectiveAction(@RequestBody CorrectiveAction correctiveAction) {
        return correctiveActionService.createCorrectiveAction(correctiveAction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorrectiveAction> updateCorrectiveAction(@PathVariable String id, @RequestBody CorrectiveAction correctiveActionDetails) {
        CorrectiveAction updatedCorrectiveAction = correctiveActionService.updateCorrectiveAction(id, correctiveActionDetails);
        return ResponseEntity.ok(updatedCorrectiveAction);
    }
}
