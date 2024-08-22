package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Event;
import com.ipactconsult.tawasalnabackendapp.payload.request.EventRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EventResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class EventMapper {
    public Event toEvent(EventRequest eventRequest) {
        return Event.builder()
                .title(eventRequest.getTitle())
                .description(eventRequest.getDescription())
                .location(eventRequest.getLocation())
                .startDate(LocalDateTime.parse(eventRequest.getStartDate()))
                .endDate(LocalDateTime.parse(eventRequest.getEndDate()))
                .category(eventRequest.getCategory())
                .status(eventRequest.getStatus())
                .imageId(eventRequest.getImageId())

                .build();
    }

    public EventResponse toEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .category(event.getCategory())
                .status(event.getStatus())
                .createdBy(event.getCreatedBy())
                .lastModifiedBy(event.getLastModifiedBy())
                .createdDate(event.getCreatedDate())
                .createdBy(event.getCreatedBy())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .imageId(event.getImageId())
                .participationCount(event.getParticipationCount())
                .participants(event.getParticipants())
                .likes(event.getLikes())
                .likeCount(event.getLikeCount())
                .build();
    }
}
