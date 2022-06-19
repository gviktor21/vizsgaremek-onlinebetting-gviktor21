package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.SportBidCreate;
import com.gviktor.onlinebet.dto.SportBidShow;
import com.gviktor.onlinebet.service.SportBidService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sportBid")
public class SportBidController {
    private SportBidService service;

    public SportBidController(SportBidService service) {
        this.service = service;
    }

    @GetMapping
    public List<SportBidShow> getAllSportBid(){
        return service.getSportBids();
    }
    @PostMapping
    public void addSportBid(@RequestBody SportBidCreate sportBidCreate, BindingResult bindingResult){
        if(service.addSportBid(sportBidCreate)){

        }
    }
    @GetMapping("/{id}")
    public SportBidShow getSportBid(@PathVariable int id){
        return service.getSportBidById(id);
    }
    @PutMapping("/{id}")
    public void updateSportBid(@PathVariable int id,@RequestBody SportBidCreate sportBidCreate){
        if(service.updateSportBid(id,sportBidCreate)){

        }
    }
    @DeleteMapping("/{id}")
    public void deleteSportBid(@PathVariable int id){
         service.deleteSportBidById(id);
    }
}
