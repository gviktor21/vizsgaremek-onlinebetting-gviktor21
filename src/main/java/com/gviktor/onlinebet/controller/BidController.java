package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.BidCreate;
import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.service.BidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(BidController.class);

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping
    public ResponseEntity<List<BidShow>> getAllBids(){
        logger.info("http get request invoked to: '/bid'");
        return new ResponseEntity<>(bidService.getAllBids(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addBid(@RequestBody @Valid BidCreate bidCreate, BindingResult bindingResult){
        if(!bindingResult.hasErrors() && bidService.addBid(bidCreate)){
            logger.info("http post request invoked to: '/bid' valid bid added");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("http post request invoked to: '/bid' failed invalid request");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BidShow> getBidById(@PathVariable int id){
        logger.info("http get request invoked to: '/bid/{id}'");
        return new ResponseEntity<>(bidService.getBidById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBid(@PathVariable int id, @RequestBody @Valid BidCreate bidCreate, BindingResult bindingResult){
        if (!bindingResult.hasErrors() && bidService.updateBid(id,bidCreate)){
            logger.info("http put request invoked to: '/bid/{id}' valid bid updated");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("http put request invoked to: '/bid/{id}' failed  invalid request");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBidById(@PathVariable int id){
        bidService.deleteBidById(id);
        logger.info("http delete request invoked to: '/bid/{id}'");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
