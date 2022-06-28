package com.gviktor.onlinebet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bidId;
    @ManyToOne
    @JoinColumn(name = "username", foreignKey=@ForeignKey(name="fk_user_bid"))
    private BidAppUser user;
    @ManyToOne
    @JoinColumn(name = "event_id", foreignKey=@ForeignKey(name="fk_user_bid_event"))
    private Event bidEvent;
    private String bidType;
    private long bidAmount;
    private long prize;
    private LocalDate date;
}
