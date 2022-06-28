package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportBidShow {
    BidShow bid;
    ParticipantShow participant;
}
