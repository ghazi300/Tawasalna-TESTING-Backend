package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Attendance;

import java.util.List;

public interface IAttendanceService {
    List<Attendance> getAllAttendances();
    Attendance getAttendanceById(String id);
    Attendance createAttendance(Attendance attendance);
    Attendance updateAttendance(String id, Attendance attendance);
    void deleteAttendance(String id);
}
