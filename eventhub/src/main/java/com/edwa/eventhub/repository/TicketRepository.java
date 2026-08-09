package com.edwa.eventhub.repository;


import com.edwa.eventhub.entity.Ticket;
import com.edwa.eventhub.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // see ticket history for a specific user
    List<Ticket> findByUserId(Long userId);

    // see tickets sold to a specific event
    List<Ticket> findByEventId(Long eventId);

    // check if a seat is already taken for a specific event
    boolean existsByEventIdAndSeatIdAndStatusIn(Long eventId, Long seatId, List<TicketStatus> statuses);
}
