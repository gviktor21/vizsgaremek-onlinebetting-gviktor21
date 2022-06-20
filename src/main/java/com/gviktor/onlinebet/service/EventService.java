package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.EventCreate;
import com.gviktor.onlinebet.dto.EventShow;
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
    private List<EventShow> convertList(List<Event> events){
        return  events.stream().map(a->mapper.map(a, EventShow.class)).collect(Collectors.toList());
    }

    public List<EventShow> getAllEvents(){
        return convertList(repository.findAll());
    }
    public void deleteEventById(int id){
        repository.deleteById(id);
    }
    public void addEvent(EventCreate eventCreate){
        repository.save(mapper.map(eventCreate,Event.class));
    }
    public void updateEvent(int id,EventCreate eventCreate){
        Event event = mapper.map(eventCreate,Event.class);
        event.setEventId(id);
        repository.save(event);
    }
    public EventShow getEventById(int id){
        return mapper.map(repository.findById(id).orElseThrow() ,EventShow.class);
    }
}
