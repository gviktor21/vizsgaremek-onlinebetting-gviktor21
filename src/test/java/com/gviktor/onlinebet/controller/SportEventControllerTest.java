package com.gviktor.onlinebet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gviktor.onlinebet.dto.SportEventShow;
import com.gviktor.onlinebet.dto.SportParticipantShow;
import com.gviktor.onlinebet.model.SportEvent;
import com.gviktor.onlinebet.model.SportParticipant;
import com.gviktor.onlinebet.model.SportType;
import com.gviktor.onlinebet.service.SportEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SportEventController.class)
@ExtendWith(SpringExtension.class)
class SportEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SportEventService sportEventService;


    private List<SportEventShow> getSportEvents(){
//        List<SportEventShow> sportEvents = new ArrayList<>();
//        SportEventShow sportEvent1 = new SportEventShow();
//        sportEvent1.setEventId(1);
//        sportEvent1.setSportType(SportType.FORMULAONE);
//        SportParticipantShow winner = new SportParticipantShow();
//        sportEvent1.setWinner();
      return  null;
    }
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