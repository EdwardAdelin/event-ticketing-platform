package com.edwa.eventhub.repository;

import com.edwa.eventhub.entity.Reservation;
import com.edwa.eventhub.entity.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // reservation history of a specific user (customer)
    List<Reservation> findByCustomer_Id(Long customerId);

    // finds pending reservations that exceeded the allowed time (pending time too big)
    List<Reservation> findByStatusAndExpiresAtBefore(ReservationStatus status, LocalDateTime time);
}
