package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Attendance;
import com.example.residentsupportservices.entity.Event;
import com.example.residentsupportservices.repository.AttendanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceService implements IAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final IEventService eventService;  // Inject the EventService to find Event

    @Override
    public Attendance createAttendance(Attendance attendance) {
        if (attendance == null || attendance.getEvent() == null || attendance.getParticipantName() == null) {
            throw new IllegalArgumentException("Attendance or required fields are missing");
        }
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance getAttendanceById(String attendanceId) {
        return attendanceRepository.findById(attendanceId).orElse(null);
    }

    @Override
    public Attendance updateAttendance(String attendanceId, Attendance attendance) {
        if (!attendanceRepository.existsById(attendanceId)) {
            throw new IllegalArgumentException("Attendance not found");
        }
        attendance.setId(attendanceId);
        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(String attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }

    @Override
    public Attendance markAttendance(String eventId, String participantName, Boolean attended) {
        Event event = eventService.getEventById(eventId);
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }
        Attendance attendance = attendanceRepository.findByEventAndParticipantName(event, participantName);
        if (attendance != null) {
            attendance.setAttended(attended);
            return attendanceRepository.save(attendance);
        } else {
            throw new IllegalArgumentException("Attendance not found for the given event and participant");
        }
    }

    @Override
    public List<Attendance> getAttendancesForEvent(String eventId) {
        Event event = eventService.getEventById(eventId);
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }
        return attendanceRepository.findByEvent(event);
    }
}
