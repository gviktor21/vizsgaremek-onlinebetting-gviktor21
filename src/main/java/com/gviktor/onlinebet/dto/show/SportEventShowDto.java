package com.gviktor.onlinebet.dto.show;

import com.gviktor.onlinebet.model.SportType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportEventShowDto {
    private int eventId;
    private SportType sportType;
    //Note it might need SportParticipant but use Participant for prevent recursion
    private ParticipantShowDto winner;
}
