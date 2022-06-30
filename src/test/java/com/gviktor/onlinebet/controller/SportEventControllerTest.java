package com.gviktor.onlinebet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.dto.EventCreate;
import com.gviktor.onlinebet.dto.SportEventCreate;
import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.dto.SportParticipantShow;
import com.gviktor.onlinebet.model.SportEvent;
import com.gviktor.onlinebet.model.SportParticipant;
import com.gviktor.onlinebet.model.SportType;
import com.gviktor.onlinebet.service.SportEventService;
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

@WebMvcTest(SportEventController.class)
@ExtendWith(SpringExtension.class)
class SportEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportEventService sportEventService;

    private String url="/sportevent";

    private List<SportEventShow> getSportEvents(){
        List<SportEventShow> sportEvents = new ArrayList<>();
        SportEventShow sportEvent1 = new SportEventShow();
        sportEvent1.setEventId(1);
        sportEvent1.setSportType(SportType.FORMULAONE);
        sportEvent1.setWinner(TestDatas.getParticipants().get(0));

        SportEventShow sportEvent2 = new SportEventShow();
        sportEvent2.setEventId(2);
        sportEvent2.setSportType(SportType.FOOTBALL);
        sportEvent2.setWinner(TestDatas.getParticipants().get(1));
        sportEvents.add(sportEvent1);
        sportEvents.add(sportEvent2);
      return  sportEvents;
    }
    private SportEventCreate getValidSportEvent(){
        SportEventCreate sportEventCreate = new SportEventCreate();
        sportEventCreate.setEventId(1);
        sportEventCreate.setSportType(SportType.FOOTBALL);
        sportEventCreate.setWinnerId(1);
        return  sportEventCreate;
    }

    private SportEventCreate getInvalidSportEvent(){
        SportEventCreate sportEventCreate = new SportEventCreate();
        sportEventCreate.setSportType(SportType.FOOTBALL);
        sportEventCreate.setWinnerId(1);
        return sportEventCreate;
    }
    @Test
    void getAllSportEvents() throws Exception{
        List<SportEventShow> sportEvents = getSportEvents();
        Mockito.when(sportEventService.getAllSportEvent()).thenReturn(sportEvents);
        mockMvc.perform(get(url)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].winner.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].eventId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].sportType",Matchers.is("FORMULAONE".toUpperCase())))
                .andExpect(jsonPath("$[1].winner.participantId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].eventId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].sportType",Matchers.is("FOOTBALL".toUpperCase())))
        ;


    }

    @Test
    void addValidSportEvent() throws Exception{
        SportEventCreate validEvent = getValidSportEvent();
        Mockito.when(sportEventService.addSportEvent(validEvent)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    void addInvalidSportEvent() throws Exception{
        SportEventCreate invalidEvent = getInvalidSportEvent();
        Mockito.when(sportEventService.addSportEvent(invalidEvent)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void getSportEvent() throws Exception {
        SportEventShow sportEvent = getSportEvents().get(0);
        Mockito.when(sportEventService.getSportEventById(sportEvent.getEventId())).thenReturn(sportEvent);
        mockMvc.perform(get(url+"/"+sportEvent.getEventId())).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.winner.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$.sportType",Matchers.is("FORMULAONE".toUpperCase())));
    }

    @Test
    void updateValidSportEvent() throws Exception {
        SportEventCreate validEvent = getValidSportEvent();
        int id=1;
        Mockito.when(sportEventService.updateSportEvent(id,validEvent)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void updateInvalidSportEvent() throws Exception {
        SportEventCreate inValid = getInvalidSportEvent();
        int id=1;
        Mockito.when(sportEventService.updateSportEvent(id,inValid)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inValid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void updateNonExistingSportEventWithValidData() throws Exception {
        SportEventCreate validEvent = getValidSportEvent();
        int id=1;
        Mockito.when(sportEventService.updateSportEvent(id,validEvent)).thenReturn(false);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void deleteSportEvent() throws Exception  {
        int id=1;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(sportEventService).deleteSportEventById(id);
    }
}