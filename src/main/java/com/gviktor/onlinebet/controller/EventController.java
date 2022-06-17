package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.EventCreate;
import com.gviktor.onlinebet.dto.EventShow;
import com.gviktor.onlinebet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventShow> getAllEvents(){
        return service.getAllEvents();
    }
    @GetMapping("/{id}")
    public EventShow getEvent(@PathVariable int id){
        return service.getEventById(id);
    }
    @PutMapping("/{id}")
    public void updateEvent(@PathVariable int i,@RequestBody EventCreate eventCreate){
        service.updateEvent(i,eventCreate);
    }
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable int id){
        service.deleteEventById(id);
    }
    @PostMapping
    public void addEvent(@RequestBody EventCreate eventCreate){
        service.addEvent(eventCreate);
    }
}
