package com.gviktor.onlinebet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.SportEventCreateDto;
import com.gviktor.onlinebet.dto.show.SportEventShowDto;
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

import java.util.List;

@WebMvcTest(SportEventController.class)
@ExtendWith(SpringExtension.class)
class SportEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportEventService sportEventService;

    private String url="/sportevent";

    @Test
    void testGetAllSportEvents() throws Exception{
        List<SportEventShowDto> sportEvents = TestDatas.getSportEvents();
        Mockito.when(sportEventService.getAllSportEvent()).thenReturn(sportEvents);
        mockMvc.perform(get(url)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].winner.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].eventId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].sportType",Matchers.is("FORMULAONE".toUpperCase())))
                .andExpect(jsonPath("$[1].winner.participantId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].eventId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].sportType",Matchers.is("FOOTBALL".toUpperCase())));
    }

    @Test
    void testAddValidSportEvent() throws Exception{
        SportEventCreateDto validEvent = TestDatas.getValidSportEvent();
        Mockito.when(sportEventService.addSportEvent(validEvent)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    void testAddInvalidSportEventReturnsBadRequest() throws Exception{
        SportEventCreateDto invalidEvent = TestDatas.getInvalidSportEvent();
        Mockito.when(sportEventService.addSportEvent(invalidEvent)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void testGetSportEventById() throws Exception {
        SportEventShowDto sportEvent = TestDatas.getSportEvents().get(0);
        Mockito.when(sportEventService.getSportEventById(sportEvent.getEventId())).thenReturn(sportEvent);
        mockMvc.perform(get(url+"/"+sportEvent.getEventId())).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.winner.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$.sportType",Matchers.is("FORMULAONE".toUpperCase())));
    }

    @Test
    void testUpdateValidSportEvent() throws Exception {
        SportEventCreateDto validEvent = TestDatas.getValidSportEvent();
        int id=1;
        Mockito.when(sportEventService.updateSportEvent(id,validEvent)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void testUpdateInvalidSportEventReturnsBadRequest() throws Exception {
        SportEventCreateDto inValid = TestDatas.getInvalidSportEvent();
        int id=1;
        Mockito.when(sportEventService.updateSportEvent(id,inValid)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inValid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void testUpdateNonExistingSportEventWithValidDataReturnsBadRequest() throws Exception {
        SportEventCreateDto validEvent = TestDatas.getValidSportEvent();
        int id=1;
        Mockito.when(sportEventService.updateSportEvent(id,validEvent)).thenReturn(false);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validEvent))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void testDeleteSportEventById() throws Exception  {
        int id=1;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(sportEventService).deleteSportEventById(id);
    }
}