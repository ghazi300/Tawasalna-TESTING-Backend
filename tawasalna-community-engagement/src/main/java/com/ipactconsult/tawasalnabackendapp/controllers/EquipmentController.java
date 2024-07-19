package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.payload.request.EquipementRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipementResponse;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.service.IEquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequiredArgsConstructor
@RequestMapping("equipment")
@CrossOrigin("*")

public class EquipmentController {
    private final IEquipmentService service;

    @PostMapping
    public ResponseEntity<String> createEquipment(@Valid @RequestBody EquipementRequest equipementRequest) {
        return ResponseEntity.ok(service.save(equipementRequest));
    }
    @GetMapping
    public ResponseEntity<List<EquipementResponse>> getAllEquipment() {
        List<EquipementResponse> equipements = service.getAllEquipment();
        return ResponseEntity.ok(equipements);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteEquipment(@PathVariable String id) {
        service.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
