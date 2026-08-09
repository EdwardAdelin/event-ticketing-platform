package com.edwa.eventhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(name = "seat_row", nullable = false) // 'row' is usually a reserved keyword in SQL
    private String row;

    @Column(name = "seat_number", nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String category; // eg: VIP, Premium, Normal
}
