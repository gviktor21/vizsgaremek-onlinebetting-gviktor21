package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.create.SportEventCreateDto;
import com.gviktor.onlinebet.dto.show.SportEventShowDto;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.model.SportEvent;
import com.gviktor.onlinebet.model.SportParticipant;
import com.gviktor.onlinebet.repository.EventRepository;
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
    private List<SportEventShowDto> convertList(List<SportEvent> sportEvents){
        return  sportEvents.stream().map(a->mapper.map(a, SportEventShowDto.class)).collect(Collectors.toList());
    }
    public List<SportEventShowDto> getAllSportEvent(){
        return convertList(repository.findAll());
    }
    public SportEventShowDto getSportEventById(int id){
        return mapper.map(repository.findById(id).orElseThrow(), SportEventShowDto.class);
    }
    public boolean isValidSportEvent(SportEventCreateDto sportEventCreateDto){
        Optional<Event> event = eventRepository.findById(sportEventCreateDto.getEventId());
        Optional<SportParticipant>  winner = participantRepository.findById(sportEventCreateDto.getWinnerId());

        return (event.isPresent() && winner.isPresent());
    }

    public boolean addSportEvent(SportEventCreateDto sportEventCreateDto){
        if (isValidSportEvent(sportEventCreateDto)){
            SportEvent sportEvent = new SportEvent();
            sportEvent.setEventId(sportEventCreateDto.getEventId());
            sportEvent.setWinner(participantRepository.findById(sportEventCreateDto.getWinnerId()).get());
            sportEvent.setSportType(sportEventCreateDto.getSportType());
            repository.save(sportEvent);
            return true;
        }
        return false;
    }
    public boolean updateSportEvent(int id, SportEventCreateDto sportEventCreateDto){
        if (repository.findById(id).isPresent() && isValidSportEvent(sportEventCreateDto)){
            SportEvent sportEvent = new SportEvent();
            sportEvent.setEventId(id);
            sportEvent.setWinner(participantRepository.findById(sportEventCreateDto.getWinnerId()).get());
            sportEvent.setSportType(sportEventCreateDto.getSportType());
            repository.save(sportEvent);
            return true;
        }
        return false;
    }
    public void deleteSportEventById(int id){
        repository.deleteById(id);
    }

}
