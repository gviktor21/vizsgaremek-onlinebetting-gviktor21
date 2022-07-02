package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.dto.create.BidAppUserCreateDto;
import com.gviktor.onlinebet.dto.show.BidAppUserShowDto;
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
        List<BidAppUserShowDto> userShows = getUsers();

        Mockito.when(userService.getAllUser()).thenReturn(userShows);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].username",Matchers.is("viktor")))
                .andExpect(jsonPath("$[1].username",Matchers.is("admin")))
                .andExpect(jsonPath("$[0].password",Matchers.is("passw")));
    }


    private List<BidAppUserShowDto> getUsers(){
        List<BidAppUserShowDto> userList = new ArrayList<>();
        BidAppUserShowDto user1 = new BidAppUserShowDto();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        BidAppUserShowDto user2 = new BidAppUserShowDto();
        user2.setPassword("passw2");
        user2.setUsername("admin");
        userList.add(user1);userList.add(user2);
        return userList;
    }
    @Test
    void testUpdateValidUser() throws Exception {
        BidAppUserCreateDto updateUserbBody = getNewValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(userService.modifyUser(updateUserbBody.getUsername(),updateUserbBody)).thenReturn(true);
        mockMvc.perform(put("/user/"+updateUserbBody.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserbBody))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
    @Test
    void testUpdateInvalidUserReturnsBadRequest() throws Exception {
        BidAppUserCreateDto updateUserBody = getNewInValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(userService.modifyUser(updateUserBody.getUsername(),updateUserBody)).thenReturn(true);
        mockMvc.perform(put("/user/"+updateUserBody.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserBody))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateValidUserSpecsAddedButNotExistReturnsBadRequest() throws Exception {
        BidAppUserCreateDto updateUserBody = getNewValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(userService.modifyUser(updateUserBody.getUsername(),updateUserBody)).thenReturn(false);
        mockMvc.perform(put("/user/"+updateUserBody.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserBody))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddValidUser() throws Exception {
        BidAppUserCreateDto newUser = getNewValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testAddInvalidUser() throws Exception {
        BidAppUserCreateDto newUser = getNewInValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private BidAppUserCreateDto getNewValidUser(){
        BidAppUserCreateDto newUser = new BidAppUserCreateDto();
        newUser.setPassword("password");
        newUser.setUsername("username");
        newUser.setEmail("valami@gmail.com");
        newUser.setAccountLevel(1);
        return newUser;
    }
    private BidAppUserCreateDto getNewInValidUser(){
        BidAppUserCreateDto newUser = new BidAppUserCreateDto();
        newUser.setPassword("pass");
        newUser.setEmail("valami@gmail.com");
        return newUser;
    }
    @Test
    void deleteUser() throws Exception {
        String username="whatever";
        mockMvc.perform(delete("/user/"+username)).andExpect(status().isOk());
        Mockito.verify(userService).deleteUser(username);
    }

    @Test
    void testgetUserById() throws Exception {
        BidAppUserShowDto bidAppUserShowDto = getUsers().get(0);
        Mockito.when(userService.getUserByUsername("viktor")).thenReturn(bidAppUserShowDto);
        mockMvc.perform(get("/user/viktor")).andExpect(status().isOk())
                .andExpect(jsonPath("$.username",Matchers.is("viktor")))
                .andExpect(jsonPath("$.password",Matchers.is("passw")));
    }
}