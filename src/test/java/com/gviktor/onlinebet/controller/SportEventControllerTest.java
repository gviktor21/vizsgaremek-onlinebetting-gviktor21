package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.service.SportEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SportEventController.class)
@ExtendWith(SpringExtension.class)
class SportEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportEventService sportEventService;


    @Test
    void getAllSportEvents() {
    }

    @Test
    void addSportEventToEventIdentifiedByItsId() {
    }

    @Test
    void getSportEvent() {
    }

    @Test
    void updateSportEvent() {
    }

    @Test
    void deleteSportEvent() {
    }
}