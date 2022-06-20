package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
@Data
@NoArgsConstructor
public class BidCreate {
    private String bidType;
    @Positive
    private long bidAmount;
    @Positive
    private long prize;
    @FutureOrPresent
    private LocalDate date;
}
