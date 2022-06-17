package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.BidAppUserCreate;
import com.gviktor.onlinebet.dto.BidAppUserShow;
import com.gviktor.onlinebet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public List<BidAppUserShow> getAllUser() {
        return userService.getAllUser();
    }

    @PutMapping("/{username}")
    public void update(@PathVariable String username,@RequestBody BidAppUserCreate bidAppUserCreate, BindingResult bindingResult){

    }
    @PostMapping
    public void addUser(@RequestBody BidAppUserCreate bidAppUserCreate,BindingResult bindingResult){
        userService.addUser(bidAppUserCreate);
    }
    @DeleteMapping("{username}")
    public void deleteUser(@PathVariable String username){
        userService.deleteUser(username);
    }
    @GetMapping("/{username}")
    public BidAppUserShow getUserById (@PathVariable String username){
        return userService.getUserByUsername(username);
    }

}
