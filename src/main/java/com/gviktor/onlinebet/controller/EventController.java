package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.EventCreate;
import com.gviktor.onlinebet.dto.EventShow;
import com.gviktor.onlinebet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<EventShow>> getAllEvents(){
        return new ResponseEntity<>(service.getAllEvents(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventShow> getEvent(@PathVariable int id) {
        return new ResponseEntity<>(service.getEventById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable int id, @RequestBody  @Valid EventCreate eventCreate, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
        }
        service.updateEvent(id,eventCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id){
        service.deleteEventById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addEvent(@RequestBody @Valid EventCreate eventCreate,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addEvent(eventCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
