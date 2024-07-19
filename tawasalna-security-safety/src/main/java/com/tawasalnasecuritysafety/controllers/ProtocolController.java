package com.tawasalnasecuritysafety.controllers;


import com.tawasalnasecuritysafety.models.Protocol;
import com.tawasalnasecuritysafety.services.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/protocols")
@CrossOrigin("*")

public class ProtocolController {
    @Autowired
    private ProtocolService protocolService;

    @PostMapping
    public Protocol createProtocol(@RequestBody Protocol protocol) {
        return protocolService.createProtocol(protocol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Protocol> getProtocolById(@PathVariable String id) {
        Optional<Protocol> protocol = protocolService.getProtocolById(id);
        return protocol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Protocol> getAllProtocols() {
        return protocolService.getAllProtocols();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Protocol> updateProtocol(@PathVariable String id, @RequestBody Protocol protocol) {
        Protocol updatedProtocol = protocolService.updateProtocol(id, protocol);
        return updatedProtocol != null ? ResponseEntity.ok(updatedProtocol) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProtocol(@PathVariable String id) {
        protocolService.deleteProtocol(id);
        return ResponseEntity.noContent().build();
    }
}
