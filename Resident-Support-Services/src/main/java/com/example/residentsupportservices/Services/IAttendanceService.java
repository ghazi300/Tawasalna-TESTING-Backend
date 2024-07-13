package com.example.residentsupportservices.Services;


import com.example.residentsupportservices.Entity.Attendance;

import java.util.List;

public interface IAttendanceService {
    List<Attendance> getAllAttendances();
    Attendance getAttendanceById(Long id);
    Attendance createAttendance(Attendance attendance);
    Attendance updateAttendance(Long id, Attendance attendance);
    void deleteAttendance(Long id);
}
