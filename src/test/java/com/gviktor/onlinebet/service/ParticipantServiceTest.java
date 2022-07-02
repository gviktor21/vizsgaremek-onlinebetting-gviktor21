package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.ParticipantCreateDto;
import com.gviktor.onlinebet.dto.show.ParticipantShowDto;
import com.gviktor.onlinebet.model.Participant;
import com.gviktor.onlinebet.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @InjectMocks
    ParticipantService participantService;

    @Mock
    ParticipantRepository participantRepository;

    @Mock
    ModelMapper modelMapper;

    @Test
    void getAllParticipant() {
        Mockito.when(participantRepository.findAll()).thenReturn(TestDatas.getParticipantEntities());
        List<ParticipantShowDto> list = participantService.getAllParticipant();
        assertEquals(2,list.size());
        Mockito.verify(participantRepository,Mockito.times(1)).findAll();
    }

    @Test
    void getParticipantById() {
        ParticipantShowDto participantShowDto = TestDatas.getParticipants().get(0);
        int id = participantShowDto.getParticipantId();
        Optional<Participant> unMappedParticipant= Optional.of(TestDatas.getParticipantEntities().get(0));
        Mockito.when(participantRepository.findById(id)).thenReturn(unMappedParticipant);
        Mockito.when(modelMapper.map(unMappedParticipant.get(), ParticipantShowDto.class)).thenReturn(participantShowDto);
        //test
        ParticipantShowDto result = participantService.getParticipantById(id);
        assertEquals(participantShowDto.getParticipantId(),result.getParticipantId());
        assertEquals(participantShowDto.getName(),result.getName());
        Mockito.verify(participantRepository,Mockito.times(1)).findById(id);
    }

    @Test
    void addParticipant() {
        ParticipantCreateDto participantCreateDto = new ParticipantCreateDto();
        Participant savedEntity = new Participant();
        Mockito.when(modelMapper.map(participantCreateDto,Participant.class)).thenReturn(savedEntity);

        participantService.addParticipant(participantCreateDto);
        Mockito.verify(participantRepository,Mockito.times(1)).save(savedEntity);
    }

    @Test
    void deleteParticipantById() {
        int id=1;
        participantService.deleteParticipantById(id);
        Mockito.verify(participantRepository,Mockito.times(1)).deleteById(id);
    }

    @Test
    void updateParticipant() {
        ParticipantCreateDto participantCreateDto = new ParticipantCreateDto();
        Participant savedEntity = new Participant();
        int id=4;
        Mockito.when(participantRepository.findById(id)).thenReturn(Optional.of(savedEntity));
        Mockito.when(modelMapper.map(participantCreateDto,Participant.class)).thenReturn(savedEntity);

        assertTrue(participantService.updateParticipant(id, participantCreateDto));
        Mockito.verify(participantRepository,Mockito.times(1)).save(savedEntity);
    }
    @Test
    void updateNonExistingParticipant() {
        ParticipantCreateDto participantCreateDto = new ParticipantCreateDto();
        Participant savedEntity = new Participant();
        int id=4;
        Mockito.when(participantRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(participantCreateDto,Participant.class)).thenReturn(savedEntity);

        assertFalse(participantService.updateParticipant(id, participantCreateDto));
        Mockito.verify(participantRepository,Mockito.times(0)).save(savedEntity);
    }
}