package com.gviktor.onlinebet.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class SportParticipantCreateDto {
    @NotNull
    private Integer participantId;
    @NotNull
    private Integer eventId;
    @Positive
    private Integer multiplier;
}
