package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.create.BidCreateDto;
import com.gviktor.onlinebet.dto.show.BidShowDto;
import com.gviktor.onlinebet.model.Bid;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.repository.BidRepository;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private List<BidShowDto> convertList(List<Bid> bids) {
        return bids.stream().map(a -> mapper.map(a, BidShowDto.class)).collect(Collectors.toList());
    }

    public List<BidShowDto> getAllBids() {
        return convertList(bidRepository.findAll());
    }

    public BidShowDto getBidById(int id) {
        return mapper.map(bidRepository.findById(id).orElseThrow(), BidShowDto.class);
    }

    public void deleteBidById(int id) {
        bidRepository.deleteById(id);
    }

    public boolean addBid(BidCreateDto bidCreateDto) {
        if (validateNewBid(bidCreateDto)){
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Bid bid = mapper.map(bidCreateDto,Bid.class);
            bid.setUser(userRepository.findById(bidCreateDto.getUsername()).get());
            bid.setBidEvent(eventRepository.findById(bidCreateDto.getEventId()).get());
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
            bidRepository.saveAndFlush(bid);
            updateUser(userRepository.findById(bidCreateDto.getUsername()).get(),bid);
            return true;
        }
        return false;
    }

    private boolean validateNewBid(BidCreateDto bidCreateDto) {
        Optional<BidAppUser> bidAppUser = userRepository.findById(bidCreateDto.getUsername());
        Optional<Event> event = eventRepository.findById(bidCreateDto.getEventId());

        return bidAppUser.isPresent() && event.isPresent()&&isNotPastEvent(event.get(), bidCreateDto)  && haveUserEnoughMoney(bidCreateDto,bidAppUser.get());
    }
    private boolean haveUserEnoughMoney(BidCreateDto bidCreateDto, BidAppUser bidAppUser){
        return bidAppUser.getBalance()- bidCreateDto.getBidAmount()>=0;
    }
    private boolean isNotPastEvent(Event event, BidCreateDto bidCreateDto){
        return bidCreateDto.getDate().isBefore(event.getStartDate());
    }
    public boolean updateBid(int id, BidCreateDto bidCreateDto) {
        if (!bidRepository.findById(id).isPresent() || !validateNewBid(bidCreateDto)) {
            return false;
        }
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Bid bid = mapper.map(bidCreateDto,Bid.class);
        bid.setBidId(id);
        bid.setUser(userRepository.findById(bidCreateDto.getUsername()).get());
        bid.setBidEvent(eventRepository.findById(bidCreateDto.getEventId()).get());
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        bidRepository.save(bid);
        return true;
    }
    private void updateUser(BidAppUser bidAppUser,Bid bid){
        bidAppUser.setBalance(bidAppUser.getBalance()-bid.getBidAmount());
        userRepository.save(bidAppUser);
    }
}
