package com.ipactconsult.tawasalnabackendapp.controllers;


import com.ipactconsult.tawasalnabackendapp.models.File;
import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.service.FileService;
import com.ipactconsult.tawasalnabackendapp.service.IEventService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("event")
@Tag(name = "event")
@CrossOrigin("*")


public class EventController {
    private final IEventService eventService;
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<String> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.save(eventRequest));
    }
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable String eventId) {
        EventResponse event = eventService.getEventById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }
    @GetMapping("/planned")
    public ResponseEntity<List<EventResponse>> getAllEventPlanned() {
        List<EventResponse> events = eventService.getAllEventPlanned();
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Void> updateEvent(@PathVariable String eventId, @Valid @RequestBody EventRequest eventRequest) {
        eventService.updateEvent(eventId, eventRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/uploadImage")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {


        File fileModel = fileService.saveFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("imageId", fileModel.getId());
            return ResponseEntity.ok(response);

    }

    @PostMapping("/{eventId}/participate")
    public ResponseEntity<String> participateInEvent(@PathVariable String eventId, @RequestParam String userId) {
        boolean success = eventService.participateInEvent(eventId, userId);

        if (success) {
            return ResponseEntity.ok("Participation successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Participation failed or user already participated");
        }
    }

    @PostMapping("/{eventId}/unparticipate")
    public ResponseEntity<String> unparticipateInEvent(@PathVariable String eventId, @RequestParam String userId) {
        boolean success = eventService.unparticipateInEvent(eventId, userId);

        if (success) {
            return ResponseEntity.ok("Unparticipation successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unparticipation failed or user did not participate");
        }
    }
    @PostMapping("/{eventId}/like")
    public ResponseEntity<String> likeEvent(@PathVariable String eventId, @RequestParam String userId) {
        boolean success = eventService.likeEvent(eventId, userId);
        if (success) {
            return ResponseEntity.ok("Like successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Like failed or user already liked");
        }
    }

    @PostMapping("/{eventId}/unlike")
    public ResponseEntity<String> unlikeEvent(@PathVariable String eventId, @RequestParam String userId) {
        boolean success = eventService.unlikeEvent(eventId, userId);
        if (success) {
            return ResponseEntity.ok("Unlike successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unlike failed or user hasn't liked yet");
        }
    }

}
