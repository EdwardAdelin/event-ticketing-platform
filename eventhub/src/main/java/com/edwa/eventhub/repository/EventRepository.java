package com.edwa.eventhub.repository;

import com.edwa.eventhub.entity.Event;
import com.edwa.eventhub.entity.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatus(EventStatus status);

    List<Event> findByCategoryAndStatus(String category, EventStatus status);

    List<Event> findByOrganizerId(Long organizerId);

    List<Event> findByDateAfterAndStatus(LocalDateTime date, EventStatus status);
}
