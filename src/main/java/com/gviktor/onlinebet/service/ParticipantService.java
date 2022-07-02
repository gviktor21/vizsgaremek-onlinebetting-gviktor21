package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.create.ParticipantCreateDto;
import com.gviktor.onlinebet.dto.show.ParticipantShowDto;
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
    private List<ParticipantShowDto> convertList(List<Participant> participants){
        return  participants.stream().map(a->mapper.map(a, ParticipantShowDto.class)).collect(Collectors.toList());
    }
    public List<ParticipantShowDto> getAllParticipant(){
        return convertList(repository.findAll());
    }
    public ParticipantShowDto getParticipantById(int id){
        return mapper.map(repository.findById(id).orElseThrow(), ParticipantShowDto.class);
    }
    public void addParticipant(ParticipantCreateDto create){
        repository.save(mapper.map(create,Participant.class));
    }
    public void deleteParticipantById(int id){
        repository.deleteById(id);
    }

    public boolean updateParticipant(int id, ParticipantCreateDto participantCreateDto){
        Participant participant = mapper.map(participantCreateDto,Participant.class);
        if (!repository.findById(id).isPresent()){
            return false;
        }
        participant.setParticipantId(id);
        repository.save(participant);
        return true;
    }
}
