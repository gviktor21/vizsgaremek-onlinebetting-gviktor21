package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.ParticipantCreate;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.model.Participant;
import com.gviktor.onlinebet.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public List<ParticipantShow> getAllParticipant(){
        return service.getAllParticipant();
    }
    @PostMapping
    public void addParticipant(@RequestBody ParticipantCreate participantCreate, BindingResult bindingResult){
        service.addParticipant(participantCreate);
    }
    @GetMapping("/{id}")
    public ParticipantShow getParticipant(@PathVariable int id){
        return service.getParticipantById(id);
    }
    @PutMapping("/{id}")
    public void modifyParticipant(@PathVariable int id,@RequestBody ParticipantCreate participantCreate){
        service.updateParticipant(id,participantCreate);
    }
    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable int id){
        service.deleteParticipantById(id);
    }

}
