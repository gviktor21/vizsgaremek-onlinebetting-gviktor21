package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gviktor.onlinebet.dto.BidAppUserCreate;
import com.gviktor.onlinebet.dto.EventCreate;
import com.gviktor.onlinebet.dto.EventShow;
import com.gviktor.onlinebet.model.EventType;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(EventController.class)
@ExtendWith(SpringExtension.class)
class EventControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventService eventService;

    @Test
    void getAllEvents() throws Exception {
        List<EventShow> eventsExpected = getEvents();
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

    private List<EventShow> getEvents(){
        List<EventShow> events = new ArrayList<>();
        EventShow eventShow1 = new EventShow();
        eventShow1.setEventId(1);
        eventShow1.setEventType(EventType.SPORT);
        eventShow1.setStartDate(LocalDate.of(1111,11,11));
        EventShow eventShow2 = new EventShow();
        eventShow2.setEventId(2);
        eventShow2.setEventType(EventType.SPORT);
        eventShow2.setStartDate(LocalDate.of(2000,10,4));
        EventShow eventShow3 = new EventShow();
        eventShow3.setEventId(3);
        eventShow3.setEventType(EventType.LOTTO5);
        eventShow3.setStartDate(LocalDate.of(2030,1,1));
        events.add(eventShow1);
        events.add(eventShow2);
        events.add(eventShow3);
        return events;
    }
    private EventCreate getValidEvent(){
        EventCreate eventCreate = new EventCreate();
        eventCreate.setEventType(EventType.LOTTO5);
        eventCreate.setDescription("Draw numbers of Lotto 5");
        eventCreate.setStartDate(LocalDate.now());
        eventCreate.setEndDate(LocalDate.now().plusDays(2));
        return eventCreate;
    }
    private EventCreate getInvalidEvent(){
        EventCreate eventCreate = new EventCreate();
        eventCreate.setEventType(EventType.LOTTO5);
        eventCreate.setDescription("Lotto".repeat(256));
        eventCreate.setStartDate(LocalDate.now());
        eventCreate.setEndDate(LocalDate.now().plusDays(2));
        return eventCreate;
    }

    @Test
    void testGetEventById() throws Exception {
        EventShow eventShow = getEvents().get(0);
        Mockito.when(eventService.getEventById(eventShow.getEventId())).thenReturn(eventShow);
        mockMvc.perform(get("/event/"+eventShow.getEventId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$.eventType",Matchers.is("SPORT")))
                .andExpect(jsonPath("$.startDate",Matchers.is("1111-11-11")));
    }

    @Test
    void testUpdateValidEvent() throws Exception {
        EventCreate newEvent = getValidEvent();
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
    void testUpdateInvalidEvent() throws Exception {
        EventCreate newEvent = getInvalidEvent();
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
    void testUpdateNonExistingElementWithValidData() throws Exception{
        EventCreate newEvent = getValidEvent();
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
    void deleteEvent() throws Exception {
        int id=1;
        mockMvc.perform(delete("/event/"+id))
                        .andExpect(status().isOk());
        Mockito.verify(eventService).deleteEventById(id);
    }

    @Test
    void testAddValidEvent() throws Exception{
        EventCreate newEvent = getValidEvent();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testAddInvalidEvent() throws Exception {
        EventCreate newEvent = getInvalidEvent();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}