package com.gviktor.onlinebet.dto;

import com.gviktor.onlinebet.model.SportParticipant;
import com.gviktor.onlinebet.model.SportType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportEventShow {
    private int eventId;
    private SportType sportType;
    private SportParticipant winner;
}
