package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.create.Lotto5CreateDto;
import com.gviktor.onlinebet.dto.show.Lotto5ShowDto;
import com.gviktor.onlinebet.service.Lotto5Service;
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
@RequestMapping("/lotto5")
public class Lotto5Controller {

    private Lotto5Service service;
    private Logger logger = LoggerFactory.getLogger(Lotto5Controller.class);

    @Autowired
    public Lotto5Controller(Lotto5Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Lotto5ShowDto>> getAllLotto5Beds(){
        logger.info("http get request invoked to: '/lotto5'");
        return new ResponseEntity<>(service.getAllLotto5(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Lotto5ShowDto> getLotto5EventResult(@PathVariable int id) {
        logger.info("http get request invoked to: '/lotto5/{id}'");
        return new ResponseEntity<>(service.getLotto5ById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addLotto5Result(@RequestBody @Valid Lotto5CreateDto lotto5CreateDto, BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.addLotto5Result(lotto5CreateDto)){
            logger.info("http post request invoked to: '/lotto5/{id}' failed due to invalid lotto5 data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http post request invoked to: '/lotto5/{id}' lotto5 result added");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyLotto5Result(@RequestBody @Valid Lotto5CreateDto lotto5CreateDto, @PathVariable int id , BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.updateLotto5Result(id, lotto5CreateDto)){
            logger.info("http put request invoked to: '/lotto5/{id}' failed due to invalid lotto5 data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http put request invoked to: '/lotto5/{id}' lotto5 updated.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLotto5Result(@PathVariable int id){
        logger.info("http delete request invoked to: '/lotto5/{id}'");
        service.deleteLotto5ResultById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
