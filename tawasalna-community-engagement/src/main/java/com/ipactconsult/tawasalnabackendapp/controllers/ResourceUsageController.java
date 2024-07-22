package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.models.ResourceUsage;
import com.ipactconsult.tawasalnabackendapp.service.ResourceUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("resource_usage")
@CrossOrigin("*")

public class ResourceUsageController {
    private final ResourceUsageService service;
    @GetMapping
    public List<ResourceUsage> getAllResourceUsages() {
        return service.getAllResourceUsages();
    }
}
