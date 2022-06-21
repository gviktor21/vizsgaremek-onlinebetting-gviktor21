package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidLotto5Create;
import com.gviktor.onlinebet.dto.BidLotto5Show;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.model.Bid;
import com.gviktor.onlinebet.model.BidLotto5;
import com.gviktor.onlinebet.model.Participant;
import com.gviktor.onlinebet.repository.BidLotto5Repository;
import com.gviktor.onlinebet.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * todo: Test that bid is for a future event that has the appropiate type.
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
        return convertList(bidLotto5Repository.findAll());
    }

    public void addBid5Lotto(BidLotto5Create bidLotto5Create) {
        if (testNumbers(bidLotto5Create)){
            bidLotto5Repository.save(mapper.map(bidLotto5Create,BidLotto5.class));
        }
    }

    public BidLotto5Show getBidLotto5ById(int id) {
        return mapper.map(bidLotto5Repository.findById(id),BidLotto5Show.class);
    }

    public void updateBid5Lotto(int id, BidLotto5Create bidLotto5Create) {
        Optional<BidLotto5> bidLotto5 = bidLotto5Repository.findById(id);
        if (bidLotto5.isPresent() && testNumbers(bidLotto5Create)){
            bidLotto5Create.setBidId(id);
            bidLotto5Repository.save(mapper.map(bidLotto5, BidLotto5.class));
        }
    }
    private boolean testNumbers(BidLotto5Create bidLotto5Create){
        Set<Integer> numbers = new HashSet<>();
        int lottoType=5;
        numbers.add(bidLotto5Create.getNumber1());
        numbers.add(bidLotto5Create.getNumber2());
        numbers.add(bidLotto5Create.getNumber3());
        numbers.add(bidLotto5Create.getNumber4());
        numbers.add(bidLotto5Create.getNumber5());
        return numbers.size()==lottoType;
    }

    public void deleteBidLotto5ById(int id) {
        bidLotto5Repository.deleteById(id);
    }
}
