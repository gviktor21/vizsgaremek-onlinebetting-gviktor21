package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.create.BidAppUserCreateDto;
import com.gviktor.onlinebet.dto.show.BidAppUserShowDto;
import com.gviktor.onlinebet.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(SportParticipantController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<BidAppUserShowDto>> getAllUser() {
        logger.info("http get request invoked to: '/user'");
        return new ResponseEntity<>( userService.getAllUser(), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> update(@PathVariable String username, @RequestBody @Valid BidAppUserCreateDto bidAppUserCreate, BindingResult bindingResult){
        if(bindingResult.hasErrors() ||  !userService.modifyUser(username,bidAppUserCreate) ){
            logger.info("http put request invoked to: '/user/{username} failed due to bad request data'");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("http put request invoked to: '/user/{username} user updated'");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody @Valid BidAppUserCreateDto bidAppUserCreate, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.info("http post request invoked to: '/user failed due to invalid request data'");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.addUser(bidAppUserCreate);
        logger.info("http post request invoked to: '/user user added'");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        logger.info("http delete request invoked to: '/user/{username}'");
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{username}")
    public ResponseEntity<BidAppUserShowDto> getUserById (@PathVariable String username){
        logger.info("http get request invoked to: '/user/{username}'");
        return new ResponseEntity<>(userService.getUserByUsername(username),HttpStatus.OK);
    }

}
