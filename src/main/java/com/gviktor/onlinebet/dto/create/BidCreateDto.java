package com.gviktor.onlinebet.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
@Data
@NoArgsConstructor
public class BidCreateDto {
    private String bidType;
    @Positive
    private long bidAmount;
    @Positive
    private long prize;
    @FutureOrPresent
    private LocalDate date;
    @NotEmpty
    private String username;
    @NotNull
    private Integer eventId;
}
