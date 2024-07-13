package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
