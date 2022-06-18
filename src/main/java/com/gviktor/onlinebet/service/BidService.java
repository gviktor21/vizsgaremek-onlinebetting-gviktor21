package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.dto.EventShow;
import com.gviktor.onlinebet.model.Bid;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.repository.BidRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Check if the user have the money to bet. The user is valid.
 * Check if the event is exists.
 * Check if the bet not for a past event.
 * Then remove the amount from the balance of the user.
 * */
@Service
public class BidService {
    private BidRepository bidRepository;
    private ModelMapper mapper;

    @Autowired
    public BidService(BidRepository bidRepository, ModelMapper modelMapper) {
        this.bidRepository = bidRepository;
        this.mapper = modelMapper;
    }
    private List<BidShow> convertList(List<Bid> bids){
        return  bids.stream().map(a->mapper.map(a, BidShow.class)).collect(Collectors.toList());
    }
    public List<BidShow> getAllBids(){
        return convertList(bidRepository.findAll());
    }
    public BidShow getBidById(int id){
        return mapper.map(bidRepository.findById(id).orElseThrow(),BidShow.class);
    }
}
