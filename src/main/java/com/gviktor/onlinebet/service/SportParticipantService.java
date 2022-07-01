package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.EventShow;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.dto.SportParticipantCreate;
import com.gviktor.onlinebet.dto.SportParticipantShow;
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
    private List<SportParticipantShow> convertList(List<SportParticipant> sportParticipants){
        return  sportParticipants.stream().map(sportParticipant->customMapping(sportParticipant)).collect(Collectors.toList());
    }
    private SportParticipantShow customMapping(SportParticipant sportParticipant){
        SportParticipantShow sportParticipantShow = new SportParticipantShow();
        sportParticipantShow.setEvent(mapper.map(eventRepository.findById(sportParticipant.getEvent().getEventId()).get(), EventShow.class));
        sportParticipantShow.setParticipant(mapper.map(sportParticipant.getParticipant(), ParticipantShow.class));
        sportParticipantShow.setId(sportParticipant.getId());
        return  sportParticipantShow;
    }
    public List<SportParticipantShow> getAllSportParticipant() {
        return convertList(repository.findAll());
    }

    public SportParticipantShow getSportParticipantById(int id) {
        return customMapping(repository.findById(id).orElseThrow());
    }

    public boolean addSportParticipant(SportParticipantCreate sportparticipantCreate) {
        Optional<SportEvent> sportEvent = sportEventRepository.findById(sportparticipantCreate.getEventId());
        Optional<Participant> participant = participantRepository.findById(sportparticipantCreate.getParticipantId());
        if(sportEvent.isPresent() && participant.isPresent() && sportEvent.get().getSportType()==participant.get().getSportType()){
            SportParticipant sportParticipant = new SportParticipant();
            sportParticipant.setParticipant(participant.get());
            sportParticipant.setEvent(sportEvent.get());
            sportParticipant.setMultiplier(sportparticipantCreate.getMultiplier());
            repository.save(sportParticipant);
            return true;
        }
        return false;
    }

    public boolean updateSportParticipant(int id, SportParticipantCreate sportparticipantCreate) {
        Optional<SportEvent> sportEvent = sportEventRepository.findById(sportparticipantCreate.getEventId());
        Optional<Participant> participant = participantRepository.findById(sportparticipantCreate.getParticipantId());
        if(sportEvent.isPresent() && participant.isPresent() && sportEvent.get().getSportType()==participant.get().getSportType()){
            SportParticipant sportParticipant = new SportParticipant();
            sportParticipant.setId(id);
            sportParticipant.setParticipant(participant.get());
            sportParticipant.setEvent(sportEvent.get());
            sportParticipant.setMultiplier(sportparticipantCreate.getMultiplier());
            repository.save(sportParticipant);
            return true;
        }
        return false;
    }

    public void deleteSportParticipantById(int id) {
        repository.deleteById(id);
    }
}
