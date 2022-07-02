package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.show.EventShowDto;
import com.gviktor.onlinebet.dto.show.ParticipantShowDto;
import com.gviktor.onlinebet.dto.create.SportParticipantCreateDto;
import com.gviktor.onlinebet.dto.show.SportParticipantShowDto;
import com.gviktor.onlinebet.model.Participant;
import com.gviktor.onlinebet.model.SportEvent;
import com.gviktor.onlinebet.model.SportParticipant;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.ParticipantRepository;
import com.gviktor.onlinebet.repository.SportEventRepository;
import com.gviktor.onlinebet.repository.SportParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SportParticipantService {
    private SportParticipantRepository repository;
    private SportEventRepository sportEventRepository;
    private EventRepository  eventRepository;
    private ParticipantRepository participantRepository;
    private ModelMapper mapper;

    @Autowired
    public SportParticipantService(SportParticipantRepository repository, SportEventRepository sportEventRepository, ParticipantRepository participantRepository,EventRepository  eventRepository, ModelMapper mapper) {
        this.repository = repository;
        this.sportEventRepository = sportEventRepository;
        this.participantRepository = participantRepository;
        this.eventRepository=eventRepository;
        this.mapper = mapper;
    }
    private List<SportParticipantShowDto> convertList(List<SportParticipant> sportParticipants){
        return  sportParticipants.stream().map(sportParticipant->customMapping(sportParticipant)).collect(Collectors.toList());
    }
    private SportParticipantShowDto customMapping(SportParticipant sportParticipant){
        SportParticipantShowDto sportParticipantShowDto = new SportParticipantShowDto();
        sportParticipantShowDto.setEvent(mapper.map(eventRepository.findById(sportParticipant.getEvent().getEventId()).get(), EventShowDto.class));
        sportParticipantShowDto.setParticipant(mapper.map(sportParticipant.getParticipant(), ParticipantShowDto.class));
        sportParticipantShowDto.setId(sportParticipant.getId());
        return sportParticipantShowDto;
    }
    public List<SportParticipantShowDto> getAllSportParticipant() {
        return convertList(repository.findAll());
    }

    public SportParticipantShowDto getSportParticipantById(int id) {
        return customMapping(repository.findById(id).orElseThrow());
    }

    public boolean addSportParticipant(SportParticipantCreateDto sportparticipantCreateDto) {
        Optional<SportEvent> sportEvent = sportEventRepository.findById(sportparticipantCreateDto.getEventId());
        Optional<Participant> participant = participantRepository.findById(sportparticipantCreateDto.getParticipantId());
        if(sportEvent.isPresent() && participant.isPresent() && sportEvent.get().getSportType()==participant.get().getSportType()){
            SportParticipant sportParticipant = new SportParticipant();
            sportParticipant.setParticipant(participant.get());
            sportParticipant.setEvent(sportEvent.get());
            sportParticipant.setMultiplier(sportparticipantCreateDto.getMultiplier());
            repository.save(sportParticipant);
            return true;
        }
        return false;
    }

    public boolean updateSportParticipant(int id, SportParticipantCreateDto sportparticipantCreateDto) {
        Optional<SportEvent> sportEvent = sportEventRepository.findById(sportparticipantCreateDto.getEventId());
        Optional<Participant> participant = participantRepository.findById(sportparticipantCreateDto.getParticipantId());
        if(sportEvent.isPresent() && participant.isPresent() && sportEvent.get().getSportType()==participant.get().getSportType()){
            SportParticipant sportParticipant = new SportParticipant();
            sportParticipant.setId(id);
            sportParticipant.setParticipant(participant.get());
            sportParticipant.setEvent(sportEvent.get());
            sportParticipant.setMultiplier(sportparticipantCreateDto.getMultiplier());
            repository.save(sportParticipant);
            return true;
        }
        return false;
    }

    public void deleteSportParticipantById(int id) {
        repository.deleteById(id);
    }
}
