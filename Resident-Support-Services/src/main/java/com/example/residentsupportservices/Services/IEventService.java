package com.example.residentsupportservices.Services;


import com.example.residentsupportservices.Entity.Event;

import java.util.List;

public interface IEventService {

    List<Event> getAllEvents();

    Event getEventById(Long id);

    Event createEvent(Event event);

    Event updateEvent(Long id, Event event);

    void deleteEvent(Long id);
}

