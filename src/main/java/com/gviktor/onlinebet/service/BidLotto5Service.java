package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.create.BidLotto5CreateDto;
import com.gviktor.onlinebet.dto.show.BidLotto5ShowDto;
import com.gviktor.onlinebet.model.*;
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
    private List<BidLotto5ShowDto> convertList(List<BidLotto5> lotto5Bids){
        return  lotto5Bids.stream().map(a->mapper.map(a, BidLotto5ShowDto.class)).collect(Collectors.toList());
    }

    public List<BidLotto5ShowDto> getAllLotto5Bids() {
        return convertList(bidLotto5Repository.findAll());
    }

    public boolean addBid5Lotto(BidLotto5CreateDto bidLotto5CreateDto) {
        if (testNumbers(bidLotto5CreateDto) && testIfValidBidAndEventExists(bidLotto5CreateDto)){
            BidLotto5 bidLotto5 = mapper.map(bidLotto5CreateDto,BidLotto5.class);
            System.out.println(bidLotto5);
            bidLotto5Repository.save(mapper.map(bidLotto5CreateDto,BidLotto5.class));
            return true;
        }
        return false;
    }

    private boolean testIfValidBidAndEventExists(BidLotto5CreateDto bidLotto5CreateDto) {
        Bid bid = bidRepository.findById(bidLotto5CreateDto.getBidId()).orElse(Bid.builder().bidId(-1).build());
        return (bid.getBidId()!=-1 && bid.getBidEvent().getEventType().equals(EventType.LOTTO5) && bid.getBidEvent().getStartDate().isAfter(bid.getDate()));
    }

    public BidLotto5ShowDto getBidLotto5ById(int id) {
        return mapper.map(bidLotto5Repository.findById(id), BidLotto5ShowDto.class);
    }

    public boolean updateBid5Lotto(int id, BidLotto5CreateDto bidLotto5CreateDto) {
        Optional<BidLotto5> bidLotto5 = bidLotto5Repository.findById(id);
        if (bidLotto5.isPresent() && testNumbers(bidLotto5CreateDto)){
            bidLotto5CreateDto.setBidId(id);
            bidLotto5Repository.save(mapper.map(bidLotto5CreateDto, BidLotto5.class));
            return true;
        }
        return false;
    }
    private boolean testNumbers(BidLotto5CreateDto bidLotto5CreateDto){
        Set<Integer> numbers = new HashSet<>();
        int lottoType=5;
        numbers.add(bidLotto5CreateDto.getNumber1());
        numbers.add(bidLotto5CreateDto.getNumber2());
        numbers.add(bidLotto5CreateDto.getNumber3());
        numbers.add(bidLotto5CreateDto.getNumber4());
        numbers.add(bidLotto5CreateDto.getNumber5());
        return numbers.size()==lottoType;
    }

    public void deleteBidLotto5ById(int id) {
        bidLotto5Repository.deleteById(id);
    }
}
