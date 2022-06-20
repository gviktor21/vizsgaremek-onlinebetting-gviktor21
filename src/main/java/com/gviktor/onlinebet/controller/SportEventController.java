package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.SportEventCreate;
import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.service.SportEventService;
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

    public SportEventController(SportEventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SportEventShow>> getAllSportEvents(){
        return new ResponseEntity<>(service.getAllSportEvent(), HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Void> addSportEventToEventIdentifiedByItsId(@PathVariable int id, @RequestBody @Valid SportEventCreate sportEventCreate, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addSportEvent(id,sportEventCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SportEventShow> getSportEvent(@PathVariable int id){
        return new ResponseEntity<>(service.getSportEventById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSportEvent(@PathVariable int id, @RequestBody @Valid SportEventCreate sportEventCreate,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        service.updateSportEvent(id,sportEventCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportEvent(@PathVariable int id){
        service.deleteSportEventById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
