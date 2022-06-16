package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantCreate {
    private String name;
    private String eventType;
}
