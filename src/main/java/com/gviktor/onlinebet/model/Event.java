package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;
    @Enumerated
    private EventType eventType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    @OneToMany(mappedBy="bidEvent")
    private List<Bid> eventBids;
}
