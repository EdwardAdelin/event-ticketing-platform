package com.edwa.eventhub.controller;


import com.edwa.eventhub.dto.ReservationRequest;
import com.edwa.eventhub.dto.ReservationResponse;
import com.edwa.eventhub.entity.Reservation;
import com.edwa.eventhub.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.createReservation(
                request.customerId(),
                request.eventId(),
                request.seatIds()
        );

        // calculate total price for response DTO
        BigDecimal totalPrice = reservation.getTickets().stream()
                .map(ticket -> ticket.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReservationResponse response = new ReservationResponse(
                reservation.getId(),
                reservation.getEvent().getTitle(),
                reservation.getTickets().size(),
                totalPrice,
                reservation.getExpiresAt(),
                reservation.getStatus().name()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}