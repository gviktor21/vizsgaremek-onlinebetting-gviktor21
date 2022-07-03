package com.gviktor.onlinebet.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class BidLotto5 {
    @Id
    @Column(name="bid_id")
    private int bidId;
    private int matches;
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;

}
