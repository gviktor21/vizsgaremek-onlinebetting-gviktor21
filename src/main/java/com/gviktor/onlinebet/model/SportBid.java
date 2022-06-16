package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class SportBid {
    @Id
    int sportBidId;
    @ManyToOne
    @JoinColumn(name="bid_id",foreignKey=@ForeignKey(name="fk_sportBid_bid"))
    private  Bid bid;
    @ManyToOne
    @JoinColumn(name= "participantId",foreignKey=@ForeignKey(name="fk_BidTo_participant"))
    private Participant participant;
}
