package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportParticipantCreate {
    private int participantId;
    private int eventId;
    private int multiplier;
}
