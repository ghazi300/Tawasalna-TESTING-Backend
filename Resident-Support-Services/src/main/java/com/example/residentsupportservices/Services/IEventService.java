package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Event;

import java.util.List;

public interface IEventService {

    List<Event> getAllEvents();

    Event getEventById(String id);

    Event createEvent(Event event);

    Event updateEvent(String id, Event event);

    void deleteEvent(String id);
}
