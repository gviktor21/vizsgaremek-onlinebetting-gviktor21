package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportParticipantShow {
    private int id;
    private ParticipantShow participant;
    private EventShow event;
}
