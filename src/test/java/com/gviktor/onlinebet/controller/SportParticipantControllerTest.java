package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.model.SportParticipant;
import com.gviktor.onlinebet.service.SportParticipantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SportParticipantController.class)
@ExtendWith(SpringExtension.class)
class SportParticipantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportParticipantService service;

    @Test
    void getAllSportParticipants() {
    }

    @Test
    void getSportParticipant() {
    }

    @Test
    void addValidSportParticipant() {
    }
    @Test
    void addInvalidSportParticipant() {
    }

    @Test
    void updateValidSportParticipant() {
    }

    @Test
    void updateInvalidSportParticipant() {
    }

    @Test
    void deleteSportParticipant() {
    }
}