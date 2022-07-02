package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.SportEventCreate;
import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.dto.SportParticipantCreate;
import com.gviktor.onlinebet.dto.SportParticipantShow;
import com.gviktor.onlinebet.service.SportParticipantService;
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
@RequestMapping("/sportParticipant")
public class SportParticipantController {
    private SportParticipantService service;
    private Logger logger = LoggerFactory.getLogger(SportParticipantController.class);

    @Autowired
    public SportParticipantController(SportParticipantService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<SportParticipantShow>> getAllSportParticipants(){
        logger.info("http get request invoked to: '/sportParticipant'");
        return new ResponseEntity<>(service.getAllSportParticipant(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SportParticipantShow> getSportParticipant(@PathVariable int id){
        logger.info("http get request invoked to: '/sportParticipant/{id}'");
        return new ResponseEntity<>(service.getSportParticipantById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addSportParticipant(@RequestBody @Valid SportParticipantCreate sportparticipantCreate, BindingResult bindingResult){
        if (bindingResult.hasErrors() ||! service.addSportParticipant(sportparticipantCreate)){
            logger.info("http post request invoked to: '/sportParticipant' failed due to bad request data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http post request invoked to: '/sportParticipant' SportParticipant added");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSportParticipant(@RequestBody @Valid SportParticipantCreate sportparticipantCreate,@PathVariable int id  ,BindingResult bindingResult){
        if (bindingResult.hasErrors() ||! service.updateSportParticipant(id,sportparticipantCreate)){
            logger.info("http put request invoked to: '/sportParticipant/{id}' failed due to bad request data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http put request invoked to: '/sportParticipant/{id}' SportParticipant updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportParticipant(@PathVariable int id){
        logger.info("http delete request invoked to: '/sportParticipant/{id}'");
        service.deleteSportParticipantById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
