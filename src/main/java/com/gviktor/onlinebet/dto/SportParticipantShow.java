package com.gviktor.onlinebet.dto;

import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.model.SportEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportParticipantShow {
    private ParticipantShow participant;
    private SportEventShow event;
}
