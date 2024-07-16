package com.example.residentsupportservices.controllers;

import com.example.residentsupportservices.entity.Attendance;
import com.example.residentsupportservices.entity.Event;
import com.example.residentsupportservices.entity.Feedback;
import com.example.residentsupportservices.entity.Participant;
import com.example.residentsupportservices.services.IAttendanceService;
import com.example.residentsupportservices.services.IEventService;
import com.example.residentsupportservices.services.IFeedbackService;
import com.example.residentsupportservices.services.IParticipantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ControlleursApi {

    private IEventService eventService;
    private IParticipantService participantService;
    private IFeedbackService feedbackService;
    private IAttendanceService attendanceService;

    // Endpoints pour l'entité Event

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable String eventId) {
        return eventService.getEventById(eventId);
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable String eventId, @RequestBody Event event) {
        return eventService.updateEvent(eventId, event);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(eventId);
    }

    // Endpoints pour l'entité Participant

    @GetMapping("/participants")
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/participants/{participantId}")
    public Participant getParticipantById(@PathVariable String participantId) {
        return participantService.getParticipantById(participantId);
    }

    @PostMapping("/participants")
    public Participant createParticipant(@RequestBody Participant participant) {
        return participantService.createParticipant(participant);
    }

    @PutMapping("/participants/{participantId}")
    public Participant updateParticipant(@PathVariable String participantId, @RequestBody Participant participant) {
        return participantService.updateParticipant(participantId, participant);
    }

    @DeleteMapping("/participants/{participantId}")
    public void deleteParticipant(@PathVariable String participantId) {
        participantService.deleteParticipant(participantId);
    }

    // Endpoints pour l'entité Feedback

    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/feedbacks/{feedbackId}")
    public Feedback getFeedbackById(@PathVariable String feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }

    @PostMapping("/feedbacks")
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.createFeedback(feedback);
    }

    @PutMapping("/feedbacks/{feedbackId}")
    public Feedback updateFeedback(@PathVariable String feedbackId, @RequestBody Feedback feedback) {
        return feedbackService.updateFeedback(feedbackId, feedback);
    }

    @DeleteMapping("/feedbacks/{feedbackId}")
    public void deleteFeedback(@PathVariable String feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
    }

    // Endpoints pour l'entité Attendance

    @GetMapping("/attendances")
    public List<Attendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/attendances/{attendanceId}")
    public Attendance getAttendanceById(@PathVariable String attendanceId) {
        return attendanceService.getAttendanceById(attendanceId);
    }

    @PostMapping("/attendances")
    public Attendance createAttendance(@RequestBody Attendance attendance) {
        return attendanceService.createAttendance(attendance);
    }

    @PutMapping("/attendances/{attendanceId}")
    public Attendance updateAttendance(@PathVariable String attendanceId, @RequestBody Attendance attendance) {
        return attendanceService.updateAttendance(attendanceId, attendance);
    }

    @DeleteMapping("/attendances/{attendanceId}")
    public void deleteAttendance(@PathVariable String attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
    }
}
