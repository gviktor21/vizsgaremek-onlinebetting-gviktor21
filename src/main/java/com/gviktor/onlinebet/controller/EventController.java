package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.create.EventCreateDto;
import com.gviktor.onlinebet.dto.show.EventShowDto;
import com.gviktor.onlinebet.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EventShowDto>> getAllEvents(){
        logger.info("http get request invoked to: '/event'");
        return new ResponseEntity<>(service.getAllEvents(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventShowDto> getEvent(@PathVariable int id) {
        logger.info("http get request invoked to: '/event/{id}'");
        return new ResponseEntity<>(service.getEventById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable int id, @RequestBody  @Valid EventCreateDto eventCreateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.updateEvent(id, eventCreateDto)){
            logger.info("http put request invoked to: '/event/{id}' failed due to bad request data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
        }
        logger.info("http put request invoked to: '/event/{id}' event updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id){
        logger.info("http delete request invoked to: '/event/{id}'");
        service.deleteEventById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addEvent(@RequestBody @Valid EventCreateDto eventCreateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            logger.info("http post request invoked to: '/event' failed due to invalid event data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http post request invoked to: '/event' event added.");
        service.addEvent(eventCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
