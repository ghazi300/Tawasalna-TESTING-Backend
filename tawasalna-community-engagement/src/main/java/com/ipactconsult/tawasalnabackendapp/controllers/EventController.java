package com.ipactconsult.tawasalnabackendapp.controllers;

import com.ipactconsult.tawasalnabackendapp.models.Event;
import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.service.IEventService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("event")
@Tag(name = "event")
@CrossOrigin("*")


public class EventController {
    private final IEventService eventService;

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

}
