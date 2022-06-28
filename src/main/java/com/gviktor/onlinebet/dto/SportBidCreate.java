package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SportBidCreate {
    @NotNull
    private Integer bidId;
    @NotNull
    private Integer participantId;
}
