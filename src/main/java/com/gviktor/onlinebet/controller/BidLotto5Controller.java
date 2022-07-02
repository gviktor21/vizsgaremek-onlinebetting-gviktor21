package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.create.BidLotto5CreateDto;
import com.gviktor.onlinebet.dto.show.BidLotto5ShowDto;
import com.gviktor.onlinebet.service.BidLotto5Service;
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
@RequestMapping("/bidlotto5")
public class BidLotto5Controller {

    private BidLotto5Service bidLotto5Service;
    private Logger logger = LoggerFactory.getLogger(BidLotto5Controller.class);

    @Autowired
    public BidLotto5Controller(BidLotto5Service bidLotto5Service) {
        this.bidLotto5Service = bidLotto5Service;
    }
    @GetMapping
    public ResponseEntity<List<BidLotto5ShowDto>> getAllLotto5Beds(){
        logger.info("http get request invoked to: '/bidlotto5'");
        return new ResponseEntity<>(bidLotto5Service.getAllLotto5Bids(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addLotto5Bid(@RequestBody @Valid BidLotto5CreateDto bidLotto5CreateDto, BindingResult bindingResult){
        if(bindingResult.hasErrors() || !bidLotto5Service.addBid5Lotto(bidLotto5CreateDto)){
            logger.info("http post request invoked to: '/bidlotto5' failed due to bad request data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http post request invoked to: '/bidlotto5' bid added");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BidLotto5ShowDto> getLotto5ById(@PathVariable int id){
        logger.info("http get request invoked to: '/bidlotto5/{id}'");
        return new ResponseEntity<>(bidLotto5Service.getBidLotto5ById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLotto5Bid(@PathVariable int id, @RequestBody @Valid BidLotto5CreateDto bidLotto5CreateDto, BindingResult bindingResult){
        if(bindingResult.hasErrors() || !bidLotto5Service.updateBid5Lotto(id, bidLotto5CreateDto)){
            logger.info("http put request invoked to: '/bidlotto5/{id}' failed due to bad request data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http put request invoked to: '/bidlotto5/{id}' update happened");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBidLotto5ById(@PathVariable int id){
        logger.info("http delete request invoked to: '/bidlotto5/{id}'");
        bidLotto5Service.deleteBidLotto5ById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
