package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.SportBidCreate;
import com.gviktor.onlinebet.dto.SportBidShow;
import com.gviktor.onlinebet.service.SportBidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sportBid")
public class SportBidController {
    private SportBidService service;

    public SportBidController(SportBidService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SportBidShow>> getAllSportBid(){
        return new ResponseEntity<>(service.getSportBids(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addSportBid(@RequestBody @Valid SportBidCreate sportBidCreate, BindingResult bindingResult){
        if(service.addSportBid(sportBidCreate) && !bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SportBidShow> getSportBid(@PathVariable int id){
        return new ResponseEntity<>(service.getSportBidById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSportBid(@PathVariable int id,@RequestBody @Valid SportBidCreate sportBidCreate,BindingResult bindingResult){
        if(service.updateSportBid(id,sportBidCreate) && !bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportBid(@PathVariable int id){
        service.deleteSportBidById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
