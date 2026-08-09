package com.edwa.eventhub.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponse(
        Long reservationId,
        String eventTitle,
        int totalSeats,
        BigDecimal totalPrice,
        LocalDateTime expiresAt,
        String status
) {}