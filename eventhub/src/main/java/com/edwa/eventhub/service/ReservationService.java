package com.edwa.eventhub.service;

import com.edwa.eventhub.entity.*;
import com.edwa.eventhub.entity.enums.EventStatus;
import com.edwa.eventhub.entity.enums.ReservationStatus;
import com.edwa.eventhub.entity.enums.TicketStatus;
import com.edwa.eventhub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// for future scaling (for extreme race conditions), a pessimistic locking might be implemented

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Transactional
    public Reservation createReservation(Long customerId, Long eventId, List<Long> seatIds) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new RuntimeException("Event is not available for booking");
        }

        // the reservation created will last 10 minutes
        Reservation reservation = Reservation.builder()
                .customer(customer)
                .event(event)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .status(ReservationStatus.PENDING)
                .build();

        reservation = reservationRepository.save(reservation);

        List<Ticket> tickets = new ArrayList<>();
        // the naming of this variable might be confusing, more simply, it is a list with RESERVED and PAID status from the enum
        List<TicketStatus> unavailableStatuses = List.of(TicketStatus.RESERVED, TicketStatus.PAID);

        // each seat is going to be processed (look up, we have received a List of seat id's, here we deal with it)
        for (Long seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

            // prevents double booking
            boolean isTaken = ticketRepository.existsByEventIdAndSeatIdAndStatusIn(eventId, seatId, unavailableStatuses);
            if (isTaken) {
                // Spring will do rollback for the transaction, so the Acid principles if the DB are respected
                // the above reservation is deleted automatically and the user receives an error
                throw new RuntimeException("Seat " + seat.getRow() + seat.getNumber() + " is already taken!");
            }

            //TODO : implement a more "mature" pricing system (change the MVP implementation)
            BigDecimal price = switch (seat.getCategory()) {
                case "VIP" -> new BigDecimal("500.00");
                case "Premium" -> new BigDecimal("300.00");
                default -> new BigDecimal("150.00");
            };

            Ticket ticket = Ticket.builder()
                    .event(event)
                    .seat(seat)
                    .user(customer)
                    .reservation(reservation)
                    .price(price)
                    .status(TicketStatus.RESERVED) // reserved for 10 minutes
                    .build();

            tickets.add(ticket);
        }

        // save the reserved tickets
        ticketRepository.saveAll(tickets);
        reservation.setTickets(tickets);

        return reservation;
    }
}