package com.gviktor.onlinebet.dto.show;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportBidShowDto {
    BidShowDto bid;
    ParticipantShowDto participant;
}
