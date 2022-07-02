package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.create.ParticipantCreateDto;
import com.gviktor.onlinebet.dto.show.ParticipantShowDto;
import com.gviktor.onlinebet.service.ParticipantService;
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
@RequestMapping("/participant")
public class ParticipantController {
    private ParticipantService service;

    @Autowired
    public ParticipantController(ParticipantService service) {
        this.service = service;
    }
    private Logger logger = LoggerFactory.getLogger(ParticipantController.class);

    @GetMapping
    public ResponseEntity<List<ParticipantShowDto>> getAllParticipant(){
        logger.info("http get request invoked to: '/participant'");
        return new ResponseEntity<>(service.getAllParticipant(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addParticipant(@RequestBody @Valid ParticipantCreateDto participantCreateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            logger.info("http post request invoked to: '/participant' failed due to invalid request data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addParticipant(participantCreateDto);
        logger.info("http post request invoked to: '/participant' participant added");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantShowDto> getParticipant(@PathVariable int id){
        logger.info("http get request invoked to: '/participant/{id}'");
        return new ResponseEntity<>(service.getParticipantById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyParticipant(@PathVariable int id, @RequestBody @Valid ParticipantCreateDto participantCreateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.updateParticipant(id, participantCreateDto)){
            logger.info("http put request invoked to: '/participant/{id}' failed due to invalid request data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http put request invoked to: '/participant/{id}' participant updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable int id){
        logger.info("http delete request invoked to: '/participant/{id}'");
        service.deleteParticipantById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
