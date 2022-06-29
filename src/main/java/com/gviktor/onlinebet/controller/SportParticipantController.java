package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.SportEventCreate;
import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.dto.SportParticipantCreate;
import com.gviktor.onlinebet.dto.SportParticipantShow;
import com.gviktor.onlinebet.service.SportParticipantService;
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

    @Autowired
    public SportParticipantController(SportParticipantService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<SportParticipantShow>> getAllSportParticipants(){
        return new ResponseEntity<>(service.getAllSportParticipant(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SportParticipantShow> getSportParticipant(@PathVariable int id){
        return new ResponseEntity<>(service.getSportParticipantById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addSportParticipant(@RequestBody @Valid SportParticipantCreate sportparticipantCreate, BindingResult bindingResult){
        if (bindingResult.hasErrors() ||! service.addSportParticipant(sportparticipantCreate)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSportParticipant(@RequestBody @Valid SportParticipantCreate sportparticipantCreate,@PathVariable int id  ,BindingResult bindingResult){
        if (bindingResult.hasErrors() ||! service.updateSportParticipant(id,sportparticipantCreate)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportParticipant(@PathVariable int id){
        service.deleteSportParticipantById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
