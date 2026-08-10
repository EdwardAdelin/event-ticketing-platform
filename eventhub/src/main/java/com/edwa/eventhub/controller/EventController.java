package com.edwa.eventhub.controller;

import com.edwa.eventhub.dto.EventCreateRequest;
import com.edwa.eventhub.dto.EventResponse;
import com.edwa.eventhub.entity.Event;
import com.edwa.eventhub.mapper.EventMapper;
import com.edwa.eventhub.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    // POST /api/events -> create event
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventCreateRequest request) {
        Event createdEvent = eventService.createEvent(
                request.title(),
                request.description(),
                request.date(),
                request.category(),
                request.venueId(),
                request.organizerId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(eventMapper.toResponse(createdEvent));
    }

    // GET /api/events -> list all active events
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAvailableEvents() {
        List<Event> events = eventService.getAvailableEvents();

        List<EventResponse> response = events.stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // GET /api/events/{id} -> details of a specific event
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventDetails(@PathVariable Long id) {
        Event event = eventService.getEventDetails(id);
        return ResponseEntity.ok(eventMapper.toResponse(event));
    }
}