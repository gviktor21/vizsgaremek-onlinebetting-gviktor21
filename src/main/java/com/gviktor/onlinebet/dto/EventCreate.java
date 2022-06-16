package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class EventCreate {
    private String eventType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
