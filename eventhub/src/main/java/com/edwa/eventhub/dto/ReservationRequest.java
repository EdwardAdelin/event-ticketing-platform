package com.edwa.eventhub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ReservationRequest(
        @NotNull(message = "Customer ID is required")
        Long customerId, // in the future we'll extract it from the JWT

        @NotNull(message = "Event ID is required")
        Long eventId,

        @NotEmpty(message = "You must select at least one seat")
        List<Long> seatIds
) {}
