package com.edwa.eventhub.dto;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        LocalDateTime date,
        String category,
        String venueName,
        String organizerName,
        String status
) {}
