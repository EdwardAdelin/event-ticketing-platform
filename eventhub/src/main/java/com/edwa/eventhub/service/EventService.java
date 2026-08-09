package com.edwa.eventhub.service;

import com.edwa.eventhub.entity.Event;
import com.edwa.eventhub.entity.User;
import com.edwa.eventhub.entity.Venue;
import com.edwa.eventhub.entity.enums.EventStatus;
import com.edwa.eventhub.repository.EventRepository;
import com.edwa.eventhub.repository.UserRepository;
import com.edwa.eventhub.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;

    @Transactional
    public Event createEvent(String title, String description, LocalDateTime date, String category, Long venueId, Long organizerId) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("Organizer not found"));
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        Event event = Event.builder()
                .title(title)
                .description(description)
                .date(date)
                .category(category)
                .venue(venue)
                .organizer(organizer)
                .status(EventStatus.PUBLISHED) // for MVP, we publish them directly
                .build();

        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getAvailableEvents() {
        return eventRepository.findByStatus(EventStatus.PUBLISHED);
    }

    @Transactional(readOnly = true)
    public Event getEventDetails(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
}
