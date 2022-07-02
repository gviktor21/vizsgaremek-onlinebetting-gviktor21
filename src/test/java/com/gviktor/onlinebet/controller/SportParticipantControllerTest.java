package com.gviktor.onlinebet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gviktor.onlinebet.dto.create.SportParticipantCreateDto;
import com.gviktor.onlinebet.dto.show.SportParticipantShowDto;
import com.gviktor.onlinebet.service.SportParticipantService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(SportParticipantController.class)
@ExtendWith(SpringExtension.class)
class SportParticipantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportParticipantService sportParticipantService;

    private String url = "/sportParticipant";
    private List<SportParticipantShowDto> getSportParticipants(){
        List<SportParticipantShowDto> sportParticipantShowDtos = new ArrayList<>();
        SportParticipantShowDto sportParticipant1 = new SportParticipantShowDto();
        sportParticipant1.setParticipant(TestDatas.getParticipants().get(0));
        sportParticipant1.setEvent(TestDatas.getEvents().get(0));
        SportParticipantShowDto sportParticipant2 = new SportParticipantShowDto();
        sportParticipant2.setParticipant(TestDatas.getParticipants().get(1));
        sportParticipant2.setEvent(TestDatas.getEvents().get(1));
        sportParticipantShowDtos.add(sportParticipant1);
        sportParticipantShowDtos.add(sportParticipant2);
        return sportParticipantShowDtos;
    }

    private SportParticipantCreateDto getValidSportParticipant(){
        SportParticipantCreateDto sportParticipantCreateDto = new SportParticipantCreateDto();
        sportParticipantCreateDto.setParticipantId(1);
        sportParticipantCreateDto.setMultiplier(10);
        sportParticipantCreateDto.setEventId(2);
        return sportParticipantCreateDto;
    }
    private SportParticipantCreateDto getInvalidSportParticipant(){
        SportParticipantCreateDto sportParticipantCreateDto = new SportParticipantCreateDto();
        sportParticipantCreateDto.setParticipantId(1);
        sportParticipantCreateDto.setMultiplier(-10);
        sportParticipantCreateDto.setEventId(2);
        return sportParticipantCreateDto;
    }

    @Test
    void getAllSportParticipants() throws Exception {
        List<SportParticipantShowDto> sportParticipants = getSportParticipants();
        Mockito.when(sportParticipantService.getAllSportParticipant()).thenReturn(sportParticipants);
        mockMvc.perform(get(url))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].participant.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].event.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].participant.name",Matchers.is("Viktor")))
                .andExpect(jsonPath("$[0].participant.sportType",Matchers.is("ROADRACECYCLING".toUpperCase())))
                .andExpect(jsonPath("$[1].participant.participantId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].event.eventId",Matchers.is(2)))
                .andExpect(status().isOk());
    }

    @Test
    void getSportParticipant() throws Exception {
        SportParticipantShowDto sportParticipantShowDto = getSportParticipants().get(1);
        Mockito.when(sportParticipantService.getSportParticipantById(sportParticipantShowDto.getId())).thenReturn(sportParticipantShowDto);

        mockMvc.perform(get(url+"/"+ sportParticipantShowDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.participant.sportType",Matchers.is("FORMULAONE".toUpperCase())))
                .andExpect(jsonPath("$.event.startDate",Matchers.is("2000-10-04")));

    }
    @Test
    void addValidSportParticipant() throws Exception {
        SportParticipantCreateDto validParticipant = getValidSportParticipant();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Mockito.when(sportParticipantService.addSportParticipant(validParticipant)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validParticipant))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    void addInvalidSportParticipant() throws Exception {
        SportParticipantCreateDto invalidParticipant = getInvalidSportParticipant();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Mockito.when(sportParticipantService.addSportParticipant(invalidParticipant)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidParticipant))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void updateValidSportParticipant() throws Exception {
        SportParticipantCreateDto validParticipant = getValidSportParticipant();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Mockito.when(sportParticipantService.updateSportParticipant(id,validParticipant)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validParticipant))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void updateInvalidSportParticipant() throws Exception {
        SportParticipantCreateDto invalidParticipant = getInvalidSportParticipant();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Mockito.when(sportParticipantService.updateSportParticipant(id,invalidParticipant)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidParticipant))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void updateNonExistingSportParticipantWithValidData() throws Exception {
        SportParticipantCreateDto validParticipant = getValidSportParticipant();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Mockito.when(sportParticipantService.updateSportParticipant(id,validParticipant)).thenReturn(false);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validParticipant))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void deleteSportParticipant() throws Exception {
        int id=1;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(sportParticipantService).deleteSportParticipantById(id);
    }
}