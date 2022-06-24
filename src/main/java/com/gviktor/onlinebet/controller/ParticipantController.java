package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.ParticipantCreate;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.service.ParticipantService;
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
    @GetMapping
    public ResponseEntity<List<ParticipantShow>> getAllParticipant(){
        return new ResponseEntity<>(service.getAllParticipant(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addParticipant(@RequestBody @Valid ParticipantCreate participantCreate, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addParticipant(participantCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantShow> getParticipant(@PathVariable int id){
        return new ResponseEntity<>(service.getParticipantById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyParticipant(@PathVariable int id,@RequestBody ParticipantCreate participantCreate, BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.updateParticipant(id,participantCreate)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable int id){
        service.deleteParticipantById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
