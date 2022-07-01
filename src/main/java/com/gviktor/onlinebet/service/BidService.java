package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidCreate;
import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.model.Bid;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.repository.BidRepository;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class BidService {
    private BidRepository bidRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private ModelMapper mapper;

    @Autowired
    public BidService(BidRepository bidRepository, ModelMapper modelMapper, UserRepository userRepository,EventRepository eventRepository) {
        this.bidRepository = bidRepository;
        this.userRepository= userRepository;
        this.eventRepository= eventRepository;
        this.mapper = modelMapper;
    }

    private List<BidShow> convertList(List<Bid> bids) {
        return bids.stream().map(a -> mapper.map(a, BidShow.class)).collect(Collectors.toList());
    }

    public List<BidShow> getAllBids() {
        return convertList(bidRepository.findAll());
    }

    public BidShow getBidById(int id) {
        return mapper.map(bidRepository.findById(id).orElseThrow(), BidShow.class);
    }

    public void deleteBidById(int id) {
        bidRepository.deleteById(id);
    }

    public boolean addBid(BidCreate bidCreate) {
        if (validateNewBid(bidCreate)){
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Bid bid = mapper.map(bidCreate,Bid.class);
            bid.setUser(userRepository.findById(bidCreate.getUsername()).get());
            bid.setBidEvent(eventRepository.findById(bidCreate.getEventId()).get());
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
            bidRepository.saveAndFlush(bid);
            updateUser(userRepository.findById(bidCreate.getUsername()).get(),bid);
            return true;
        }
        return false;
    }

    private boolean validateNewBid(BidCreate bidCreate) {
        Optional<BidAppUser> bidAppUser = userRepository.findById(bidCreate.getUsername());
        Optional<Event> event = eventRepository.findById(bidCreate.getEventId());

        return bidAppUser.isPresent() && event.isPresent()&&isNotPastEvent(event.get(),bidCreate)  && haveUserEnoughMoney(bidCreate,bidAppUser.get());
    }
    private boolean haveUserEnoughMoney(BidCreate bidCreate,BidAppUser bidAppUser){
        return bidAppUser.getBalance()-bidCreate.getBidAmount()>=0;
    }
    private boolean isNotPastEvent(Event event, BidCreate bidCreate){
        return bidCreate.getDate().isBefore(event.getStartDate());
    }
    public boolean updateBid(int id, BidCreate bidCreate) {
        if (!bidRepository.findById(id).isPresent() || !validateNewBid(bidCreate)) {
            return false;
        }
        Bid bid = mapper.map(bidCreate, Bid.class);
        bid.setBidId(id);
        bidRepository.save(bid);
        return true;
    }
    private void updateUser(BidAppUser bidAppUser,Bid bid){
        bidAppUser.setBalance(bidAppUser.getBalance()-bid.getBidAmount());
        userRepository.save(bidAppUser);
    }
}
