package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SportBidCreate {
    @NotBlank
    @NotNull
    private int bidId;
    @NotBlank
    @NotNull
    private int participantId;
}
