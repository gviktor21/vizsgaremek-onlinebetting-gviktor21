package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.*;
import com.gviktor.onlinebet.service.Lotto5Service;
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

    @Autowired
    public Lotto5Controller(Lotto5Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Lotto5Show>> getAllLotto5Beds(){
        return new ResponseEntity<>(service.getAllLotto5(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Lotto5Show> getEvent(@PathVariable int id) {
        return new ResponseEntity<>(service.getLotto5ById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addLotto5Result(@RequestBody @Valid Lotto5Create lotto5Create, BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.addLotto5Result(lotto5Create)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Void> modifyLotto5Result(@RequestBody @Valid Lotto5Create lotto5Create,@PathVariable int id ,BindingResult bindingResult){
        if (bindingResult.hasErrors() || !service.updateLotto5Result(id, lotto5Create)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteLotto5Result(@PathVariable int id){
        service.deleteLotto5ResultById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
