package com.gviktor.onlinebet.dto;

import com.gviktor.onlinebet.model.SportType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SportEventCreate {
    @NotBlank
    @NotNull
    private SportType sportType;
    private int winnerId;
}
