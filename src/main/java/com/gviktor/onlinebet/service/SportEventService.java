package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.dto.SportEventCreate;
import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.model.Bid;
import com.gviktor.onlinebet.model.SportEvent;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.ParticipantRepository;
import com.gviktor.onlinebet.repository.SportEventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Check event is exists. Just Event that exist.
 * Get winner if exists.
 * */
@Service
public class SportEventService {
    private SportEventRepository repository;
    private EventRepository eventRepository;
    private ParticipantRepository participantRepository;
    private ModelMapper mapper;

    public SportEventService(SportEventRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    private List<SportEventShow> convertList(List<SportEvent> sportEvents){
        return  sportEvents.stream().map(a->mapper.map(a, SportEventShow.class)).collect(Collectors.toList());
    }
    public List<SportEventShow> getAllSportEvent(){
        return convertList(repository.findAll());
    }
    public SportEventShow getSportEventById(int id){
        return mapper.map(repository.findById(id).orElseThrow(),SportEventShow.class);
    }
    public  void addSportEvent(int id,SportEventCreate sportEventCreate){
        //todo check if there is such an event in the database
        //todo check if the two is te same event type
        SportEvent sportEvent= mapper.map(sportEventCreate,SportEvent.class);
        repository.save(sportEvent);
    }
    public void updateSportEvent(int id,SportEventCreate sportEventCreate){
        SportEvent sportEvent= mapper.map(sportEventCreate,SportEvent.class);
        repository.save(sportEvent);
    }
    public void deleteSportEventById(int id){
        repository.deleteById(id);
    }

}
