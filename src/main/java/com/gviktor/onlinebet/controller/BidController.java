package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.BidCreate;
import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/bid")
public class BidController {

    private BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping
    public List<BidShow> getAllBids(){
        return bidService.getAllBids();
    }
    @PostMapping
    public void addBid(@RequestBody BidCreate bidCreate, BindingResult bindingResult){
        if(bidService.addBid(bidCreate)){

        }
    }
    @GetMapping("/{id}")
    public BidShow getBidById(@PathVariable int id){
        return bidService.getBidById(id);
    }
    @PutMapping("/{id}")
    public void updateBid(@PathVariable int id,@RequestBody BidCreate bidCreate, BindingResult bindingResult){
        if (bidService.updateBid(id,bidCreate)){

        }
    }
    @DeleteMapping("/{id}")
    public void deleteBidById(@PathVariable int id){
        bidService.deleteBidById(id);
    }


}
