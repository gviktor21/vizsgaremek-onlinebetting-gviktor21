package com.gviktor.onlinebet.dto;

import com.gviktor.onlinebet.model.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class EventShow {
    private int eventId;
    private EventType eventType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
