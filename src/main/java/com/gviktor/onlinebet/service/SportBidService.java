package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidAppUserShow;
import com.gviktor.onlinebet.dto.SportBidShow;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.model.SportBid;
import com.gviktor.onlinebet.repository.SportBidRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportBidService {
    private ModelMapper mapper;
    private SportBidRepository sportBidRepository;

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

}
