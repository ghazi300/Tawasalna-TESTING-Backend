package com.tawasalna.tawasalnacrisis.controllers;

import com.tawasalna.tawasalnacrisis.models.Resource;
import com.tawasalna.tawasalnacrisis.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
@CrossOrigin("*")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        Resource savedResource = resourceService.saveResource(resource);
        return ResponseEntity.ok(savedResource);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable String id) {
        Resource resource = resourceService.getResourceById(id);
        if (resource != null) {
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }
}
