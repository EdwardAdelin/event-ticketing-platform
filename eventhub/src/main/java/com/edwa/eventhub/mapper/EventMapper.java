package com.edwa.eventhub.mapper;

import com.edwa.eventhub.dto.EventResponse;
import com.edwa.eventhub.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getCategory(),
                event.getVenue().getName(),
                event.getOrganizer().getFirstName() + " " + event.getOrganizer().getLastName(),
                event.getStatus().name()
        );
    }
}
