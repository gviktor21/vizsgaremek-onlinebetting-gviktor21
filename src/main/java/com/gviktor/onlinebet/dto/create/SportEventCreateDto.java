package com.gviktor.onlinebet.dto.create;

import com.gviktor.onlinebet.model.SportType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SportEventCreateDto {
    @NotNull
    private Integer eventId;
    @NotNull
    private SportType sportType;
    private int winnerId;
}
