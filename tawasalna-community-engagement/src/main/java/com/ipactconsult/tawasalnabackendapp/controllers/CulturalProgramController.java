package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.payload.request.CulturalRequest;
import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.CulturalResponse;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.service.ICulturalProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("cultural")
@CrossOrigin("*")

public class CulturalProgramController  {
    private final ICulturalProgramService service;

    @PostMapping
    public ResponseEntity<String> createProgram(@Valid @RequestBody CulturalRequest culturalRequest) {
        return ResponseEntity.ok(service.save(culturalRequest));
    }
    @GetMapping
    public ResponseEntity<List<CulturalResponse>> getAllPrograms() {
        List<CulturalResponse> culturalResponseList = service.getAllPrograms();
        return ResponseEntity.ok(culturalResponseList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable String id) {
        service.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }
    //gafddfdfdf


}
