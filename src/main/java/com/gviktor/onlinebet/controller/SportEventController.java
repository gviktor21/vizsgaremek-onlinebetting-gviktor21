package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.SportEventCreate;
import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.service.SportEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sportevent")
public class SportEventController {
    private SportEventService service;

    public SportEventController(SportEventService service) {
        this.service = service;
    }

    @GetMapping
    public List<SportEventShow> getAllSportEvents(){
        return service.getAllSportEvent();
    }
    @PostMapping("/{id}")
    public void addSportEventToEventIdentifiedByItsId(@PathVariable int id, @RequestBody SportEventCreate sportEventCreate){
        service.addSportEvent(id,sportEventCreate);
    }
    @GetMapping("/{id}")
    public SportEventShow getSportEvent(@PathVariable int id){
        return service.getSportEventById(id);
    }
    @PutMapping("/{id}")
    public void updateSportEvent(@PathVariable int id, @RequestBody SportEventCreate sportEventCreate){
        service.updateSportEvent(id,sportEventCreate);
    }
    @PutMapping("/{id}")
    public void deleteSportEvent(@PathVariable int id){
        service.deleteSportEventById(id);
    }

}
