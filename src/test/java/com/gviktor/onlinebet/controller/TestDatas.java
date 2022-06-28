package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.model.SportType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDatas {
    public static List<BidShow> getBids(){
        List<BidShow> bids = new ArrayList<>();
        BidShow bidShow1= new BidShow();
        bidShow1.setBidId(1);
        bidShow1.setBidAmount(1000);
        bidShow1.setBidType("sport");
        bidShow1.setDate(LocalDate.of(2022,11,2));
        bidShow1.setPrize(20000);
        BidShow bidShow2= new BidShow();
        bidShow2.setBidId(2);
        bidShow2.setBidAmount(330);
        bidShow2.setBidType("lotto");
        bidShow2.setDate(LocalDate.of(2022,8,2));
        bidShow2.setPrize(2000);
        bids.add(bidShow1);
        bids.add(bidShow2);
        return bids;
    }

    public static List<ParticipantShow> getParticipants(){
        List<ParticipantShow> participants = new ArrayList<>();

        ParticipantShow participantShow1 = new ParticipantShow();
        participantShow1.setParticipantId(1);
        participantShow1.setName("Viktor");
        participantShow1.setSportType(SportType.ROADRACECYCLING);

        ParticipantShow participantShow2 = new ParticipantShow();
        participantShow2.setParticipantId(2);
        participantShow2.setName("Soros");
        participantShow2.setSportType(SportType.FORMULAONE);

        participants.add(participantShow1);
        participants.add(participantShow2);
        return participants;
    }
}
