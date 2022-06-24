package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.dto.BidAppUserCreate;
import com.gviktor.onlinebet.dto.BidAppUserShow;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void testGetAllUser() throws Exception {
        List<BidAppUserShow> userShows = getUsers();

        Mockito.when(userService.getAllUser()).thenReturn(userShows);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].username",Matchers.is("viktor")))
                .andExpect(jsonPath("$[1].username",Matchers.is("admin")))
                .andExpect(jsonPath("$[0].password",Matchers.is("passw")));
    }


    private List<BidAppUserShow> getUsers(){
        List<BidAppUserShow> userList = new ArrayList<>();
        BidAppUserShow user1 = new BidAppUserShow();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        BidAppUserShow user2 = new BidAppUserShow();
        user2.setPassword("passw2");
        user2.setUsername("admin");
        userList.add(user1);userList.add(user2);
        return userList;
    }
    @Test
    void testUpdateValidUser() {
    }
    @Test
    void testUpdateInvalidUser() {
    }
    @Test
    void testAddValidUser() throws Exception {
        BidAppUserCreate newUser = getNewValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testAddInvalidUser() throws Exception {
        BidAppUserCreate newUser = getNewInValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private BidAppUserCreate getNewValidUser(){
        BidAppUserCreate newUser = new BidAppUserCreate();
        newUser.setPassword("password");
        newUser.setUsername("username");
        newUser.setEmail("valami@gmail.com");
        newUser.setAccountLevel(1);
        return newUser;
    }
    private BidAppUserCreate getNewInValidUser(){
        BidAppUserCreate newUser = new BidAppUserCreate();
        newUser.setPassword("pass");
        newUser.setEmail("valami@gmail.com");
        return newUser;
    }
    @Test
    void deleteUser() {
    }

    @Test
    void testgetUserById() throws Exception {
        BidAppUserShow bidAppUserShow = getUsers().get(0);
        Mockito.when(userService.getUserByUsername("viktor")).thenReturn(bidAppUserShow);
        mockMvc.perform(get("/user/viktor")).andExpect(status().isOk())
                .andExpect(jsonPath("$.username",Matchers.is("viktor")))
                .andExpect(jsonPath("$.password",Matchers.is("passw")));
    }
}