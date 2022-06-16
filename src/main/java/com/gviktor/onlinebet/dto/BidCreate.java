package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class BidCreate {
    private String bidType;
    private long bidAmount;
    private long prize;
    private LocalDate date;
}
