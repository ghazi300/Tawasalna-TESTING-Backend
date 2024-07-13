package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
