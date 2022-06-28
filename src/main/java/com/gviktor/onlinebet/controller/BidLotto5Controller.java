package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.BidLotto5Create;
import com.gviktor.onlinebet.dto.BidLotto5Show;
import com.gviktor.onlinebet.service.BidLotto5Service;
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

    @Autowired
    public BidLotto5Controller(BidLotto5Service bidLotto5Service) {
        this.bidLotto5Service = bidLotto5Service;
    }
    @GetMapping
    public ResponseEntity<List<BidLotto5Show>> getAllLotto5Beds(){
        return new ResponseEntity<>(bidLotto5Service.getAllLotto5Bids(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addLotto5Bid(@RequestBody @Valid BidLotto5Create bidLotto5Create, BindingResult bindingResult){
        if(bindingResult.hasErrors() || !bidLotto5Service.addBid5Lotto(bidLotto5Create)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BidLotto5Show> getLotto5ById(@PathVariable int id){
        return new ResponseEntity<>(bidLotto5Service.getBidLotto5ById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLotto5Bid(@PathVariable int id,@RequestBody @Valid BidLotto5Create bidLotto5Create, BindingResult bindingResult){
        if(bindingResult.hasErrors() || !bidLotto5Service.updateBid5Lotto(id,bidLotto5Create)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBidLotto5ById(@PathVariable int id){
        bidLotto5Service.deleteBidLotto5ById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
