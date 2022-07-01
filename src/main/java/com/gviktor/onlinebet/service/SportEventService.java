package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.SportEventCreate;
import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.model.SportEvent;
import com.gviktor.onlinebet.model.SportParticipant;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.ParticipantRepository;
import com.gviktor.onlinebet.repository.SportEventRepository;
import com.gviktor.onlinebet.repository.SportParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Check event is exists. Just Event that exist.
 * Get winner if exists.
 *  //todo check if the winner participant has te same event type and participitate
 * */
@Service
public class SportEventService {
    private SportEventRepository repository;
    private EventRepository eventRepository;
    private SportParticipantRepository participantRepository;
    private ModelMapper mapper;

    public SportEventService(SportEventRepository repository, ModelMapper mapper,EventRepository eventRepository,SportParticipantRepository participantRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventRepository = eventRepository;
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
    public boolean isValidSportEvent(SportEventCreate sportEventCreate){
        Optional<Event> event = eventRepository.findById(sportEventCreate.getEventId());
        Optional<SportParticipant>  winner = participantRepository.findById(sportEventCreate.getWinnerId());

        return (event.isPresent() && winner.isPresent());
    }

    public boolean addSportEvent(SportEventCreate sportEventCreate){
        if (isValidSportEvent(sportEventCreate)){
            SportEvent sportEvent = new SportEvent();
            sportEvent.setEventId(sportEventCreate.getEventId());
            sportEvent.setWinner(participantRepository.findById(sportEventCreate.getWinnerId()).get());
            sportEvent.setSportType(sportEventCreate.getSportType());
            repository.save(sportEvent);
            return true;
        }
        return false;
    }
    public boolean updateSportEvent(int id, SportEventCreate sportEventCreate){
        if (repository.findById(id).isPresent() && isValidSportEvent(sportEventCreate)){
            SportEvent sportEvent = new SportEvent();
            sportEvent.setEventId(id);
            sportEvent.setWinner(participantRepository.findById(sportEventCreate.getWinnerId()).get());
            sportEvent.setSportType(sportEventCreate.getSportType());
            repository.save(sportEvent);
            return true;
        }
        return false;
    }
    public void deleteSportEventById(int id){
        repository.deleteById(id);
    }

}
