package com.gviktor.onlinebet.dto.show;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BidShowDto {
    private int bidId;
    private BidAppUserShowDto user;
    private EventShowDto bidEvent;
    private String bidType;
    private long bidAmount;
    private long prize;
    private LocalDate date;
}
