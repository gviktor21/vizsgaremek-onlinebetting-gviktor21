package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidAppUserShow;
import com.gviktor.onlinebet.dto.SportBidCreate;
import com.gviktor.onlinebet.dto.SportBidShow;
import com.gviktor.onlinebet.model.Bid;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.model.Participant;
import com.gviktor.onlinebet.model.SportBid;
import com.gviktor.onlinebet.repository.BidRepository;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.ParticipantRepository;
import com.gviktor.onlinebet.repository.SportBidRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportBidService {
    private ModelMapper mapper;
    private SportBidRepository sportBidRepository;
    private ParticipantRepository participantRepository;
    private BidRepository bidRepository;

    /*
    *todo: Check if participant is exist
    *todo: check if the sport eventType is equivalent with the participant sport_type and the participant is attending that sportEvent
    *todo: refactor for sportParticipant
    * */
    public SportBidService(ModelMapper mapper, SportBidRepository sportBidRepository) {
        this.mapper = mapper;
        this.sportBidRepository = sportBidRepository;
    }

    public List<SportBidShow> getSportBids(){
        return convertList(sportBidRepository.findAll());
    }
    private List<SportBidShow> convertList(List<SportBid> sportBids){
        return  sportBids.stream().map(a->mapper.map(a,SportBidShow.class)).collect(Collectors.toList());
    }
    public SportBidShow getSportBidById(int id){
        return mapper.map(sportBidRepository.findById(id).orElseThrow(),SportBidShow.class);
    }

    public boolean addSportBid(SportBidCreate sportBidCreate){
        SportBid sportBid =checkAndGetSportBid(sportBidCreate);
        if (sportBid!= null){
            sportBidRepository.save(sportBid);
            return true;
        }
        return false;
    }
    public boolean updateSportBid(int id,SportBidCreate sportBidCreate){
        if (!sportBidRepository.findById(id).isPresent()){
            return false;
        }
        SportBid sportBid = checkAndGetSportBid(sportBidCreate);
        if (sportBidCreate == null){
            sportBidRepository.save(sportBid);
            return true;
        }
        return false;
    }
    private SportBid checkAndGetSportBid(SportBidCreate sportBidCreate){
        Participant participant = participantRepository.findById(sportBidCreate.getParticipantId()).orElseThrow();
        Bid bid = bidRepository.findById(sportBidCreate.getBidId()).orElseThrow();
        SportBid sportBid = new SportBid();
        if(participant != null && bid != null) {
            sportBid.setBid(bid);
            sportBid.setParticipant(participant);
            return  sportBid;
        }
        sportBid=null;
        return  sportBid;
    }
    public void deleteSportBidById(int id){
        sportBidRepository.deleteById(id);

    }
}
