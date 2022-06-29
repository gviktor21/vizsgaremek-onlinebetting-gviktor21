package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.controller.TestDatas;
import com.gviktor.onlinebet.dto.BidAppUserCreate;
import com.gviktor.onlinebet.dto.BidAppUserShow;
import com.gviktor.onlinebet.dto.EventCreate;
import com.gviktor.onlinebet.dto.EventShow;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.UserRepository;
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
class EventServiceTest {

    @InjectMocks
    EventService eventService;

    @Mock
    EventRepository eventRepository;

    @Mock
    ModelMapper modelMapper;

    @Test
    void getAllEvents() {
        Mockito.when(eventRepository.findAll()).thenReturn(TestDatas.getEventEntities());
        List<EventShow> list = eventService.getAllEvents();
        assertEquals(3,list.size());
        Mockito.verify(eventRepository,Mockito.times(1)).findAll();
    }

    @Test
    void deleteEventById() {
        int id=1;
        eventService.deleteEventById(id);
        Mockito.verify(eventRepository,Mockito.times(1)).deleteById(id);
    }

    @Test
    void addEvent() {
        EventCreate eventCreate = new EventCreate();
        Event savedEntity = new Event();
        Mockito.when(modelMapper.map(eventCreate,Event.class)).thenReturn(savedEntity);

        eventService.addEvent(eventCreate);
        Mockito.verify(eventRepository,Mockito.times(1)).save(savedEntity);
    }

    @Test
    void updateExistingEvent() {
        EventCreate eventCreate = new EventCreate();
        Event savedEntity = new Event();
        int id=4;
        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.of(savedEntity));
        Mockito.when(modelMapper.map(eventCreate,Event.class)).thenReturn(savedEntity);

        assertTrue(eventService.updateEvent(id,eventCreate));
        Mockito.verify(eventRepository,Mockito.times(1)).save(savedEntity);
    }
    @Test
    void updateNonExistingEvent() {
        EventCreate eventCreate = new EventCreate();
        Event savedEntity = new Event();
        int id=4;
        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(eventCreate,Event.class)).thenReturn(savedEntity);

        assertFalse(eventService.updateEvent(id,eventCreate));
        Mockito.verify(eventRepository,Mockito.times(0)).save(savedEntity);
    }
    @Test
    void getEventById() {
        EventShow eventShow = TestDatas.getEvents().get(0);
        int id = eventShow.getEventId();
        Optional<Event> unMappedEvent= Optional.of(TestDatas.getEventEntities().get(0));
        Mockito.when(eventRepository.findById(id)).thenReturn(unMappedEvent);
        Mockito.when(modelMapper.map(unMappedEvent.get(), EventShow.class)).thenReturn(eventShow);
        //test
        EventShow result = eventService.getEventById(id);
        assertEquals(eventShow.getEventId(),result.getEventId());
        assertEquals(eventShow.getDescription(),result.getDescription());
        Mockito.verify(eventRepository,Mockito.times(1)).findById(id);
    }
}