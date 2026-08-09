package com.edwa.eventhub.service;

import com.edwa.eventhub.entity.Reservation;
import com.edwa.eventhub.entity.enums.ReservationStatus;
import com.edwa.eventhub.entity.Ticket;
import com.edwa.eventhub.entity.enums.TicketStatus;
import com.edwa.eventhub.repository.ReservationRepository;
import com.edwa.eventhub.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationCleanupService {

    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;

    // 60000 milliseconds = 60 seconds
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();

        // find pending reservation that have passed their allowed pending time
        List<Reservation> expiredReservations = reservationRepository
                .findByStatusAndExpiresAtBefore(ReservationStatus.PENDING, now);

        if (expiredReservations.isEmpty()) {
            return;
        }

        log.info("Found {} expired reservations. Cancelling them...", expiredReservations.size());

        for (Reservation reservation : expiredReservations) {
            // cancel reservation
            reservation.setStatus(ReservationStatus.EXPIRED);

            /* cancel tickets and seats (seats are not really canceled, but we can say that they are
            canceled because the reservation/tickets makes reference to them. Deleting that so-called reference means that those seats are freed. */
            List<Ticket> tickets = reservation.getTickets();
            for (Ticket ticket : tickets) {
                ticket.setStatus(TicketStatus.CANCELLED);
            }

            ticketRepository.saveAll(tickets);
        }

        reservationRepository.saveAll(expiredReservations);
        log.info("Successfully cancelled expired reservations and freed the seats.");
    }
}
