package com.gviktor.onlinebet.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SportBidCreateDto {
    @NotNull
    private Integer bidId;
    @NotNull
    private Integer participantId;
}
