package com.gviktor.onlinebet.dto.create;

import com.gviktor.onlinebet.model.EventType;
import com.gviktor.onlinebet.model.SportType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ParticipantCreateDto {
    @NotBlank
    @NotNull
    private String name;
    @NotNull
    private SportType sportType;
}
