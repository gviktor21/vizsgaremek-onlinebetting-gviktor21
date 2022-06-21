package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidLotto5Create;
import com.gviktor.onlinebet.dto.BidLotto5Show;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.model.BidLotto5;
import com.gviktor.onlinebet.model.Participant;
import com.gviktor.onlinebet.repository.BidLotto5Repository;
import com.gviktor.onlinebet.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Test that bid is for a future event that has the appropiate type.
 *
 * */
@Service
public class BidLotto5Service {
    private BidLotto5Repository bidLotto5Repository;
    private EventRepository eventRepository;
    private ModelMapper mapper;

    @Autowired
    public BidLotto5Service(BidLotto5Repository bidLotto5Repository, EventRepository eventRepository, ModelMapper modelMapper) {
        this.bidLotto5Repository = bidLotto5Repository;
        this.eventRepository = eventRepository;
        this.mapper = modelMapper;
    }
    private List<BidLotto5Show> convertList(List<BidLotto5> lotto5Bids){
        return  lotto5Bids.stream().map(a->mapper.map(a, BidLotto5Show.class)).collect(Collectors.toList());
    }

    public List<BidLotto5Show> getAllLotto5Bids() {
    }

    public void addBid5Lotto(BidLotto5Create bidLotto5Create) {
    }

    public BidLotto5Show getBidLotto5ById(int id) {
    }

    public void updateBid5Lotto(int id, BidLotto5Create bidLotto5Create) {
    }

    public void deleteBidLotto5ById(int id) {
    }
}
