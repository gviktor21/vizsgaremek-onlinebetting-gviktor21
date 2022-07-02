package com.gviktor.onlinebet.dto.show;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportParticipantShowDto {
    private int id;
    private ParticipantShowDto participant;
    private EventShowDto event;
}
