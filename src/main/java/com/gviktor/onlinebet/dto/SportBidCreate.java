package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SportBidCreate {
    private int bid_id;
    private int participant_id;
}
