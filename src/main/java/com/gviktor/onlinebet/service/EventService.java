package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.create.EventCreateDto;
import com.gviktor.onlinebet.dto.show.EventShowDto;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private EventRepository repository;
    private ModelMapper mapper;

    @Autowired
    public EventService(EventRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    private List<EventShowDto> convertList(List<Event> events){
        return  events.stream().map(a->mapper.map(a, EventShowDto.class)).collect(Collectors.toList());
    }

    public List<EventShowDto> getAllEvents(){
        return convertList(repository.findAll());
    }
    public void deleteEventById(int id){
        repository.deleteById(id);
    }
    public void addEvent(EventCreateDto eventCreateDto){
        repository.save(mapper.map(eventCreateDto,Event.class));
    }
    public boolean updateEvent(int id, EventCreateDto eventCreateDto){
        Event event = mapper.map(eventCreateDto,Event.class);
        event.setEventId(id);
        if (!repository.findById(id).isPresent()){
            return false;
        }
            repository.save(event);
        return true;
    }
    public EventShowDto getEventById(int id){
        return mapper.map(repository.findById(id).orElseThrow() , EventShowDto.class);
    }
}
