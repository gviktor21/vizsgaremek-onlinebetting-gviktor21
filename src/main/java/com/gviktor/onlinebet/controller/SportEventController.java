package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.create.SportEventCreateDto;
import com.gviktor.onlinebet.dto.show.SportEventShowDto;
import com.gviktor.onlinebet.service.SportEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sportevent")
public class SportEventController {

    private SportEventService service;
    private Logger logger = LoggerFactory.getLogger(SportEventController.class);

    public SportEventController(SportEventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SportEventShowDto>> getAllSportEvents(){
        logger.info("http get request invoked to: '/sportevent'");
        return new ResponseEntity<>(service.getAllSportEvent(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addSportEventToEventIdentifiedByItsId(@RequestBody @Valid SportEventCreateDto sportEventCreateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors() || ! service.addSportEvent(sportEventCreateDto)){
            logger.info("http post request invoked to: '/sportevent' failed due to bad request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http post request invoked to: '/sportevent' SportEvent added");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SportEventShowDto> getSportEvent(@PathVariable int id){
        logger.info("http get request invoked to: '/sportevent/{id}'");
        return new ResponseEntity<>(service.getSportEventById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSportEvent(@PathVariable int id, @RequestBody @Valid SportEventCreateDto sportEventCreateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.updateSportEvent(id, sportEventCreateDto)){
            logger.info("http put request invoked to: '/sportevent/{id}' failed due to bad request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http put request invoked to: '/sportevent/{id}' SportEvent updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportEvent(@PathVariable int id){
        logger.info("http delete request invoked to: '/sportevent/{id}'");
        service.deleteSportEventById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
