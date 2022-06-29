package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.ParticipantCreate;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.model.Participant;
import com.gviktor.onlinebet.repository.ParticipantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private ParticipantRepository repository;
    private ModelMapper mapper;

    public ParticipantService(ParticipantRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    private List<ParticipantShow> convertList(List<Participant> participants){
        return  participants.stream().map(a->mapper.map(a, ParticipantShow.class)).collect(Collectors.toList());
    }
    public List<ParticipantShow> getAllParticipant(){
        return convertList(repository.findAll());
    }
    public ParticipantShow getParticipantById(int id){
        return mapper.map(repository.findById(id).orElseThrow(),ParticipantShow.class);
    }
    public void addParticipant(ParticipantCreate create){
        repository.save(mapper.map(create,Participant.class));
    }
    public void deleteParticipantById(int id){
        repository.deleteById(id);
    }

    public boolean updateParticipant(int id, ParticipantCreate participantCreate){
        Participant participant = mapper.map(participantCreate,Participant.class);
        if (!repository.findById(id).isPresent()){
            return false;
        }
        participant.setParticipantId(id);
        repository.save(participant);
        return true;
    }
}
