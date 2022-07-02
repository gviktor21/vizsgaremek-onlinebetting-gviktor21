package com.gviktor.onlinebet.dto.show;

import com.gviktor.onlinebet.model.EventType;
import com.gviktor.onlinebet.model.SportType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantShowDto {
    private int participantId;
    private String name;
    private SportType sportType;
}
