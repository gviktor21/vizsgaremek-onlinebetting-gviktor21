package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidLotto5Create;
import com.gviktor.onlinebet.dto.BidLotto5Show;
import com.gviktor.onlinebet.model.Bid;
import com.gviktor.onlinebet.model.BidLotto5;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.model.EventType;
import com.gviktor.onlinebet.repository.BidLotto5Repository;
import com.gviktor.onlinebet.repository.BidRepository;
import com.gviktor.onlinebet.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BidLotto5Service {
    private BidLotto5Repository bidLotto5Repository;
    private EventRepository eventRepository;
    private BidRepository bidRepository;
    private ModelMapper mapper;

    @Autowired
    public BidLotto5Service(BidLotto5Repository bidLotto5Repository, EventRepository eventRepository, ModelMapper modelMapper, BidRepository bidRepository) {
        this.bidLotto5Repository = bidLotto5Repository;
        this.eventRepository = eventRepository;
        this.bidRepository = bidRepository;
        this.mapper = modelMapper;
    }
    private List<BidLotto5Show> convertList(List<BidLotto5> lotto5Bids){
        return  lotto5Bids.stream().map(a->mapper.map(a, BidLotto5Show.class)).collect(Collectors.toList());
    }

    public List<BidLotto5Show> getAllLotto5Bids() {
        return convertList(bidLotto5Repository.findAll());
    }

    public boolean addBid5Lotto(BidLotto5Create bidLotto5Create) {
        if (testNumbers(bidLotto5Create) && testIfValidBidAndEventExists(bidLotto5Create)){
            bidLotto5Repository.save(mapper.map(bidLotto5Create,BidLotto5.class));
            return true;
        }
        return false;
    }

    private boolean testIfValidBidAndEventExists(BidLotto5Create bidLotto5Create) {
        Bid bid = bidRepository.findById(bidLotto5Create.getBidId()).orElse(Bid.builder().bidId(-1).build());
        Optional<Event> event = eventRepository.findById(bid.getBidId());
        return (event.isPresent() && event.get().getEventType().equals(EventType.LOTTO5) && event.get().getStartDate().isAfter(bid.getDate()));
    }

    public BidLotto5Show getBidLotto5ById(int id) {
        return mapper.map(bidLotto5Repository.findById(id),BidLotto5Show.class);
    }

    public boolean updateBid5Lotto(int id, BidLotto5Create bidLotto5Create) {
        Optional<BidLotto5> bidLotto5 = bidLotto5Repository.findById(id);
        if (bidLotto5.isPresent() && testNumbers(bidLotto5Create)){
            bidLotto5Create.setBidId(id);
            bidLotto5Repository.save(mapper.map(bidLotto5, BidLotto5.class));
            return true;
        }
        return false;
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
