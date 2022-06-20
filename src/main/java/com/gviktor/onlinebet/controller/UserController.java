package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.BidAppUserCreate;
import com.gviktor.onlinebet.dto.BidAppUserShow;
import com.gviktor.onlinebet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<BidAppUserShow>> getAllUser() {
        return new ResponseEntity<>( userService.getAllUser(), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> update(@PathVariable String username,@RequestBody @Valid BidAppUserCreate bidAppUserCreate, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.modifyUser(username,bidAppUserCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody @Valid BidAppUserCreate bidAppUserCreate,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.addUser(bidAppUserCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{username}")
    public ResponseEntity<BidAppUserShow> getUserById (@PathVariable String username){
        return new ResponseEntity<>(userService.getUserByUsername(username),HttpStatus.OK);
    }

}
