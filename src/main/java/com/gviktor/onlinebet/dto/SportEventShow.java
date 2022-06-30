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
    //Note it might need SportParticipant but use Participant for prevent recursion
    private ParticipantShow winner;
}
