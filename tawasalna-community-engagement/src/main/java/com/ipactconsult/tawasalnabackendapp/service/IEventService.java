package com.ipactconsult.tawasalnabackendapp.service;


import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;


import java.util.List;


public interface IEventService {
    String save(EventRequest eventRequest);


    List<EventResponse> getAllEvents();

    EventResponse getEventById(String eventId);

    void updateEvent(String eventId, EventRequest eventRequest);

    void deleteEvent(String eventId);

    List<EventResponse> getAllEventPlanned();

    boolean participateInEvent(String eventId, String userId);

    boolean unparticipateInEvent(String eventId, String userId);

    boolean likeEvent(String eventId, String userId);

    boolean unlikeEvent(String eventId, String userId);
}
