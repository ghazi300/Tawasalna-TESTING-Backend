package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Event;
import com.example.residentsupportservices.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements IEventService {

    private EventRepository eventRepository;


    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(String id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElse(null); // Handle optional if necessary
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(String id, Event event) {
        if (!eventRepository.existsById(id)) {
            // Handle not found scenario or throw exception
            return null;
        }
        event.setId(id); // Set the ID to ensure update on existing entity
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}
