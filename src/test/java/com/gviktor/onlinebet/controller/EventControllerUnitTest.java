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
import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.EventCreateDto;
import com.gviktor.onlinebet.dto.show.EventShowDto;
import com.gviktor.onlinebet.service.EventService;
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

@WebMvcTest(EventController.class)
@ExtendWith(SpringExtension.class)
class EventControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventService eventService;

    @Test
    void getAllEvents() throws Exception {
        List<EventShowDto> eventsExpected = TestDatas.getEvents();
        Mockito.when(eventService.getAllEvents()).thenReturn(eventsExpected);

        mockMvc.perform(get("/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].eventId",Matchers.is(1)))
                .andExpect(jsonPath("$[1].eventId",Matchers.is(2)))
                .andExpect(jsonPath("$[0].eventType",Matchers.is("Sport".toUpperCase())))
                .andExpect(jsonPath("$[2].eventId",Matchers.is(3)))
                .andExpect(jsonPath("$[2].startDate",Matchers.is("2030-01-01")));
    }


    @Test
    void testGetEventById() throws Exception {
        EventShowDto eventShowDto = TestDatas.getEvents().get(0);
        Mockito.when(eventService.getEventById(eventShowDto.getEventId())).thenReturn(eventShowDto);
        mockMvc.perform(get("/event/"+ eventShowDto.getEventId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$.eventType",Matchers.is("SPORT")))
                .andExpect(jsonPath("$.startDate",Matchers.is("1111-11-11")));
    }

    @Test
    void testUpdateValidEvent() throws Exception {
        EventCreateDto newEvent = TestDatas.getValidEvent();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Mockito.when(eventService.updateEvent(id,newEvent)).thenReturn(true);
        mockMvc.perform(put("/event/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testUpdateInvalidEventReturnsBadRequest() throws Exception {
        EventCreateDto newEvent = TestDatas.getInvalidEvent();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Mockito.when(eventService.updateEvent(id,newEvent)).thenReturn(true);
        mockMvc.perform(put("/event/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testUpdateNonExistingElementWithValidDataReturnsBadRequest() throws Exception{
        EventCreateDto newEvent = TestDatas.getValidEvent();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Mockito.when(eventService.updateEvent(id,newEvent)).thenReturn(false);
        mockMvc.perform(put("/event/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testDeleteEvent() throws Exception {
        int id=1;
        mockMvc.perform(delete("/event/"+id))
                        .andExpect(status().isOk());
        Mockito.verify(eventService).deleteEventById(id);
    }

    @Test
    void testAddValidEvent() throws Exception{
        EventCreateDto newEvent = TestDatas.getValidEvent();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testAddInvalidEventReturnsBadRequest() throws Exception {
        EventCreateDto newEvent = TestDatas.getInvalidEvent();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}