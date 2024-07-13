package com.example.residentsupportservices.Controlleur;


import com.example.residentsupportservices.Entity.Attendance;
import com.example.residentsupportservices.Entity.Event;
import com.example.residentsupportservices.Entity.Feedback;
import com.example.residentsupportservices.Entity.Participant;
import com.example.residentsupportservices.Services.IAttendanceService;
import com.example.residentsupportservices.Services.IEventService;
import com.example.residentsupportservices.Services.IFeedbackService;
import com.example.residentsupportservices.Services.IParticipantService;
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
    public Event getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event event) {
        return eventService.updateEvent(eventId, event);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }

    // Endpoints pour l'entité Participant

    @GetMapping("/participants")
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/participants/{participantId}")
    public Participant getParticipantById(@PathVariable Long participantId) {
        return participantService.getParticipantById(participantId);
    }

    @PostMapping("/participants")
    public Participant createParticipant(@RequestBody Participant participant) {
        return participantService.createParticipant(participant);
    }

    @PutMapping("/participants/{participantId}")
    public Participant updateParticipant(@PathVariable Long participantId, @RequestBody Participant participant) {
        return participantService.updateParticipant(participantId, participant);
    }

    @DeleteMapping("/participants/{participantId}")
    public void deleteParticipant(@PathVariable Long participantId) {
        participantService.deleteParticipant(participantId);
    }

    // Endpoints pour l'entité Feedback

    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/feedbacks/{feedbackId}")
    public Feedback getFeedbackById(@PathVariable Long feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }

    @PostMapping("/feedbacks")
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.createFeedback(feedback);
    }

    @PutMapping("/feedbacks/{feedbackId}")
    public Feedback updateFeedback(@PathVariable Long feedbackId, @RequestBody Feedback feedback) {
        return feedbackService.updateFeedback(feedbackId, feedback);
    }

    @DeleteMapping("/feedbacks/{feedbackId}")
    public void deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
    }

    // Endpoints pour l'entité Attendance

    @GetMapping("/attendances")
    public List<Attendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/attendances/{attendanceId}")
    public Attendance getAttendanceById(@PathVariable Long attendanceId) {
        return attendanceService.getAttendanceById(attendanceId);
    }

    @PostMapping("/attendances")
    public Attendance createAttendance(@RequestBody Attendance attendance) {
        return attendanceService.createAttendance(attendance);
    }

    @PutMapping("/attendances/{attendanceId}")
    public Attendance updateAttendance(@PathVariable Long attendanceId, @RequestBody Attendance attendance) {
        return attendanceService.updateAttendance(attendanceId, attendance);
    }

    @DeleteMapping("/attendances/{attendanceId}")
    public void deleteAttendance(@PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
    }
}
