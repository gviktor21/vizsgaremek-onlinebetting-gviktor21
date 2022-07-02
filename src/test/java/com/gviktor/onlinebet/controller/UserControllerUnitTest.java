package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.TestDatas;
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
        List<BidAppUserShowDto> userShows = TestDatas.getUsers();

        Mockito.when(userService.getAllUser()).thenReturn(userShows);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].username",Matchers.is("viktor")))
                .andExpect(jsonPath("$[1].username",Matchers.is("admin")))
                .andExpect(jsonPath("$[0].password",Matchers.is("passw")));
    }

    @Test
    void testUpdateValidUser() throws Exception {
        BidAppUserCreateDto updateUserbBody = TestDatas.getNewValidUser();
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
        BidAppUserCreateDto updateUserBody = TestDatas.getNewInValidUser();
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
        BidAppUserCreateDto updateUserBody = TestDatas.getNewValidUser();
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
        BidAppUserCreateDto newUser = TestDatas.getNewValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testAddInvalidUserReturnsBadRequest() throws Exception {
        BidAppUserCreateDto newUser = TestDatas.getNewInValidUser();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteUserById() throws Exception {
        String username="whatever";
        mockMvc.perform(delete("/user/"+username)).andExpect(status().isOk());
        Mockito.verify(userService).deleteUser(username);
    }

    @Test
    void testGetUserByUsername() throws Exception {
        BidAppUserShowDto bidAppUserShowDto = TestDatas.getUsers().get(0);
        Mockito.when(userService.getUserByUsername("viktor")).thenReturn(bidAppUserShowDto);
        mockMvc.perform(get("/user/viktor")).andExpect(status().isOk())
                .andExpect(jsonPath("$.username",Matchers.is("viktor")))
                .andExpect(jsonPath("$.password",Matchers.is("passw")));
    }
}