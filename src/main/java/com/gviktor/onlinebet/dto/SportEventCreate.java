package com.gviktor.onlinebet.dto;

import com.gviktor.onlinebet.model.SportType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SportEventCreate {
    @NotBlank
    @NotNull
    private SportType sportType;
    private int winnerId;
}
