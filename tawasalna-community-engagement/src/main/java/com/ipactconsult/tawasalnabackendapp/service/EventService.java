package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Event;
import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import com.ipactconsult.tawasalnabackendapp.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor


public class EventService implements IEventService{
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    @Override
    public String save(EventRequest eventRequest) {
        Event event = eventMapper.toEvent(eventRequest);
        return eventRepository.save(event).getId();
    }

    @Override
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toEventResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse getEventById(String eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        return eventOptional.map(eventMapper::toEventResponse).orElse(null);
    }

    @Override
    public void updateEvent(String eventId, EventRequest eventRequest) {

    }

    @Override
    public void deleteEvent(String eventId) {
        eventRepository.deleteById(eventId);

    }


}
