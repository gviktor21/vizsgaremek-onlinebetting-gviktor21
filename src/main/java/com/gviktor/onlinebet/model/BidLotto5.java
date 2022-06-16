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
    @OneToOne
    @PrimaryKeyJoinColumn(name="bid_id", referencedColumnName="bid_id")
    protected Bid bid;
    private int matches;
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;

}
