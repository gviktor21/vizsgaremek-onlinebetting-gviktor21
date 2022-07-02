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
import com.gviktor.onlinebet.dto.create.EventCreateDto;
import com.gviktor.onlinebet.dto.show.EventShowDto;
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

@WebMvcTest(EventController.class)
@ExtendWith(SpringExtension.class)
class EventControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventService eventService;

    @Test
    void getAllEvents() throws Exception {
        List<EventShowDto> eventsExpected = getEvents();
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

    private List<EventShowDto> getEvents(){
        List<EventShowDto> events = new ArrayList<>();
        EventShowDto eventShowDto1 = new EventShowDto();
        eventShowDto1.setEventId(1);
        eventShowDto1.setEventType(EventType.SPORT);
        eventShowDto1.setStartDate(LocalDate.of(1111,11,11));
        EventShowDto eventShowDto2 = new EventShowDto();
        eventShowDto2.setEventId(2);
        eventShowDto2.setEventType(EventType.SPORT);
        eventShowDto2.setStartDate(LocalDate.of(2000,10,4));
        EventShowDto eventShowDto3 = new EventShowDto();
        eventShowDto3.setEventId(3);
        eventShowDto3.setEventType(EventType.LOTTO5);
        eventShowDto3.setStartDate(LocalDate.of(2030,1,1));
        events.add(eventShowDto1);
        events.add(eventShowDto2);
        events.add(eventShowDto3);
        return events;
    }
    private EventCreateDto getValidEvent(){
        EventCreateDto eventCreateDto = new EventCreateDto();
        eventCreateDto.setEventType(EventType.LOTTO5);
        eventCreateDto.setDescription("Draw numbers of Lotto 5");
        eventCreateDto.setStartDate(LocalDate.now());
        eventCreateDto.setEndDate(LocalDate.now().plusDays(2));
        return eventCreateDto;
    }
    private EventCreateDto getInvalidEvent(){
        EventCreateDto eventCreateDto = new EventCreateDto();
        eventCreateDto.setEventType(EventType.LOTTO5);
        eventCreateDto.setDescription("Lotto".repeat(256));
        eventCreateDto.setStartDate(LocalDate.now());
        eventCreateDto.setEndDate(LocalDate.now().plusDays(2));
        return eventCreateDto;
    }

    @Test
    void testGetEventById() throws Exception {
        EventShowDto eventShowDto = getEvents().get(0);
        Mockito.when(eventService.getEventById(eventShowDto.getEventId())).thenReturn(eventShowDto);
        mockMvc.perform(get("/event/"+ eventShowDto.getEventId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$.eventType",Matchers.is("SPORT")))
                .andExpect(jsonPath("$.startDate",Matchers.is("1111-11-11")));
    }

    @Test
    void testUpdateValidEvent() throws Exception {
        EventCreateDto newEvent = getValidEvent();
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
        EventCreateDto newEvent = getInvalidEvent();
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
        EventCreateDto newEvent = getValidEvent();
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
        EventCreateDto newEvent = getValidEvent();
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
        EventCreateDto newEvent = getInvalidEvent();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}