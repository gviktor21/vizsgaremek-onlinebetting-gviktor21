package com.gviktor.onlinebet.dto;

import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BidShow {
    private int bidId;
    private BidAppUser user;
    private Event bidEvent;
    private String bidType;
    private long bidAmount;
    private long prize;
    private LocalDate date;
}
