package com.example.residentsupportservices.Services;

import com.example.residentsupportservices.Entity.Attendance;
import com.example.residentsupportservices.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService implements IAttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance getAttendanceById(Long id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        return attendance.orElse(null); // Handle optional if necessary
    }

    @Override
    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance updateAttendance(Long id, Attendance attendance) {
        if (!attendanceRepository.existsById(id)) {
            // Handle not found scenario or throw exception
            return null;
        }
        attendance.setId(id); // Set the ID to ensure update on existing entity
        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}
