package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.EventCreateDto;
import com.gviktor.onlinebet.dto.show.EventShowDto;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.repository.EventRepository;
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
        List<EventShowDto> list = eventService.getAllEvents();
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
        EventCreateDto eventCreateDto = new EventCreateDto();
        Event savedEntity = new Event();
        Mockito.when(modelMapper.map(eventCreateDto,Event.class)).thenReturn(savedEntity);

        eventService.addEvent(eventCreateDto);
        Mockito.verify(eventRepository,Mockito.times(1)).save(savedEntity);
    }

    @Test
    void updateExistingEvent() {
        EventCreateDto eventCreateDto = new EventCreateDto();
        Event savedEntity = new Event();
        int id=4;
        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.of(savedEntity));
        Mockito.when(modelMapper.map(eventCreateDto,Event.class)).thenReturn(savedEntity);

        assertTrue(eventService.updateEvent(id, eventCreateDto));
        Mockito.verify(eventRepository,Mockito.times(1)).save(savedEntity);
    }
    @Test
    void updateNonExistingEvent() {
        EventCreateDto eventCreateDto = new EventCreateDto();
        Event savedEntity = new Event();
        int id=4;
        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(eventCreateDto,Event.class)).thenReturn(savedEntity);

        assertFalse(eventService.updateEvent(id, eventCreateDto));
        Mockito.verify(eventRepository,Mockito.times(0)).save(savedEntity);
    }
    @Test
    void getEventById() {
        EventShowDto eventShowDto = TestDatas.getEvents().get(0);
        int id = eventShowDto.getEventId();
        Optional<Event> unMappedEvent= Optional.of(TestDatas.getEventEntities().get(0));
        Mockito.when(eventRepository.findById(id)).thenReturn(unMappedEvent);
        Mockito.when(modelMapper.map(unMappedEvent.get(), EventShowDto.class)).thenReturn(eventShowDto);
        //test
        EventShowDto result = eventService.getEventById(id);
        assertEquals(eventShowDto.getEventId(),result.getEventId());
        assertEquals(eventShowDto.getDescription(),result.getDescription());
        Mockito.verify(eventRepository,Mockito.times(1)).findById(id);
    }
}