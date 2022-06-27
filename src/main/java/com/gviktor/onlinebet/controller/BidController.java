package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.BidCreate;
import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/bid")
public class BidController {

    private BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping
    public ResponseEntity<List<BidShow>> getAllBids(){
        return new ResponseEntity<>(bidService.getAllBids(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addBid(@RequestBody @Valid BidCreate bidCreate, BindingResult bindingResult){
        if(bidService.addBid(bidCreate) && !bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BidShow> getBidById(@PathVariable int id){
        return new ResponseEntity<>(bidService.getBidById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBid(@PathVariable int id, @RequestBody @Valid BidCreate bidCreate, BindingResult bindingResult){
        if (bidService.updateBid(id,bidCreate) && !bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBidById(@PathVariable int id){
        bidService.deleteBidById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
