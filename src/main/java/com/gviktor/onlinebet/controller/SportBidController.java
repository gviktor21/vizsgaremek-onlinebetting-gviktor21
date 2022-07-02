package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.SportBidCreate;
import com.gviktor.onlinebet.dto.SportBidShow;
import com.gviktor.onlinebet.service.SportBidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(SportBidController.class);

    @GetMapping
    public ResponseEntity<List<SportBidShow>> getAllSportBid(){
        logger.info("http get request invoked to: '/sportBid'");
        return new ResponseEntity<>(service.getSportBids(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addSportBid(@RequestBody @Valid SportBidCreate sportBidCreate, BindingResult bindingResult){
        if(!bindingResult.hasErrors() && service.addSportBid(sportBidCreate) ){
            logger.info("http post request invoked to: '/sportBid' SportBid added");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("http post request invoked to: '/sportBid' failed due to invalid requst data");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SportBidShow> getSportBid(@PathVariable int id){
        logger.info("http get request invoked to: '/sportBid/{id}'");
        return new ResponseEntity<>(service.getSportBidById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSportBid(@PathVariable int id,@RequestBody @Valid SportBidCreate sportBidCreate,BindingResult bindingResult){
        if(!bindingResult.hasErrors() && service.updateSportBid(id,sportBidCreate) ){
            logger.info("http put request invoked to: '/sportBid/{id} sportBid updated'");

            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("http put request invoked to: '/sportBid/{id}' failed due to invalid requst data");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportBid(@PathVariable int id){
        logger.info("http delete request invoked to: '/sportBid/{id}'");
        service.deleteSportBidById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
