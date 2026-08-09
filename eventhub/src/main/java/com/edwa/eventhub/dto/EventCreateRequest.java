package com.edwa.eventhub.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EventCreateRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Date is required")
        @Future(message = "Event date must be in the future")
        LocalDateTime date,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Venue ID is required")
        Long venueId,

        @NotNull(message = "Organizer ID is required")
        Long organizerId // later, this ID will be taken directly from the JWT token
) {}