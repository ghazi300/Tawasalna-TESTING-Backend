package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Event;
import com.ipactconsult.tawasalnabackendapp.models.StatusEvent;
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

    @Override
    public List<EventResponse> getAllEventPlanned() {
        List<Event> events = eventRepository.findByStatus(StatusEvent.planned);
        return events.stream()
                .map(eventMapper::toEventResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean participateInEvent(String eventId, String userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if(eventOptional.isPresent())
        {
            Event event=eventOptional.get();
            if(event.getParticipants().contains(userId))
            {
                return false;
            }
            event.getParticipants().add(userId);
            event.setParticipationCount(event.getParticipationCount()+1);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean unparticipateInEvent(String eventId, String userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (!event.getParticipants().contains(userId)) {
                return false;
            }
            event.getParticipants().remove(userId);
            event.setParticipationCount(event.getParticipationCount() - 1);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean likeEvent(String eventId, String userId) {

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (!event.getLikes().contains(userId)) {
                event.getLikes().add(userId);
                event.setLikeCount(event.getLikeCount() + 1);
                eventRepository.save(event);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unlikeEvent(String eventId, String userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (event.getLikes().contains(userId)) {
                event.getLikes().remove(userId);
                event.setLikeCount(event.getLikeCount() - 1);
                eventRepository.save(event);
                return true;
            }
        }
        return false;
    }


}
