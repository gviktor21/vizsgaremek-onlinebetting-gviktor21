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
import com.gviktor.onlinebet.dto.SportBidShow;
import com.gviktor.onlinebet.dto.SportParticipantCreate;
import com.gviktor.onlinebet.dto.SportParticipantShow;
import com.gviktor.onlinebet.model.SportParticipant;
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

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SportParticipantController.class)
@ExtendWith(SpringExtension.class)
class SportParticipantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportParticipantService sportParticipantService;

    private String url = "/sportParticipant";
    private List<SportParticipantShow> getSportParticipants(){
        List<SportParticipantShow> sportParticipantShows = new ArrayList<>();
        SportParticipantShow sportParticipant1 = new SportParticipantShow();
        sportParticipant1.setParticipant(TestDatas.getParticipants().get(0));
        sportParticipant1.setEvent(TestDatas.getEvents().get(0));
        SportParticipantShow sportParticipant2 = new SportParticipantShow();
        sportParticipant2.setParticipant(TestDatas.getParticipants().get(1));
        sportParticipant2.setEvent(TestDatas.getEvents().get(1));
        sportParticipantShows.add(sportParticipant1);
        sportParticipantShows.add(sportParticipant2);
        return sportParticipantShows;
    }

    private SportParticipantCreate getValidSportParticipant(){
        SportParticipantCreate sportParticipantCreate = new SportParticipantCreate();
        sportParticipantCreate.setParticipantId(1);
        sportParticipantCreate.setMultiplier(10);
        sportParticipantCreate.setEventId(2);
        return sportParticipantCreate;
    }
    private SportParticipantCreate getInvalidSportParticipant(){
        SportParticipantCreate sportParticipantCreate = new SportParticipantCreate();
        sportParticipantCreate.setParticipantId(1);
        sportParticipantCreate.setMultiplier(-10);
        sportParticipantCreate.setEventId(2);
        return sportParticipantCreate;
    }

    @Test
    void getAllSportParticipants() throws Exception {
        List<SportParticipantShow> sportParticipants = getSportParticipants();
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
        SportParticipantShow sportParticipantShow = getSportParticipants().get(1);
        Mockito.when(sportParticipantService.getSportParticipantById(sportParticipantShow.getId())).thenReturn(sportParticipantShow);

        mockMvc.perform(get(url+"/"+sportParticipantShow.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.participant.sportType",Matchers.is("FORMULAONE".toUpperCase())))
                .andExpect(jsonPath("$.event.startDate",Matchers.is("2000-10-04")));

    }
    @Test
    void addValidSportParticipant() throws Exception {
        SportParticipantCreate validParticipant = getValidSportParticipant();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Mockito.when(sportParticipantService.addSportParticipant(validParticipant)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validParticipant))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    void addInvalidSportParticipant() throws Exception {
        SportParticipantCreate invalidParticipant = getInvalidSportParticipant();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Mockito.when(sportParticipantService.addSportParticipant(invalidParticipant)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidParticipant))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void updateValidSportParticipant() throws Exception {
        SportParticipantCreate validParticipant = getValidSportParticipant();
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
        SportParticipantCreate invalidParticipant = getInvalidSportParticipant();
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
        SportParticipantCreate validParticipant = getValidSportParticipant();
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