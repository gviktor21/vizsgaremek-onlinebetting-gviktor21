package com.gviktor.onlinebet.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.dto.ParticipantCreate;
import com.gviktor.onlinebet.dto.ParticipantShow;
import com.gviktor.onlinebet.model.SportType;
import com.gviktor.onlinebet.service.ParticipantService;
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

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(ParticipantController.class)
@ExtendWith(SpringExtension.class)
class ParticipantControllerTest {

    @MockBean
    ParticipantService service;

    @Autowired
    MockMvc mockMvc;
    private List<ParticipantShow> getParticipants(){
        List<ParticipantShow> participants = new ArrayList<>();

        ParticipantShow participantShow1 = new ParticipantShow();
        participantShow1.setParticipantId(1);
        participantShow1.setName("Viktor");
        participantShow1.setSportType(SportType.ROADRACECYCLING);

        ParticipantShow participantShow2 = new ParticipantShow();
        participantShow2.setParticipantId(2);
        participantShow2.setName("Soros");
        participantShow2.setSportType(SportType.FORMULAONE);

        participants.add(participantShow1);
        participants.add(participantShow2);
        return participants;
    }

    private ParticipantCreate getValidParticipant(){
        ParticipantCreate participantCreate = new ParticipantCreate();
        participantCreate.setName("Viktor");
        participantCreate.setSportType(SportType.FORMULAONE);
        return participantCreate;
    }
    private ParticipantCreate getInvalidParticipant(){
        ParticipantCreate participantCreate = new ParticipantCreate();
        participantCreate.setName("");
        participantCreate.setSportType(SportType.FORMULAONE);
        return participantCreate;
    }
    @Test
    void getAllParticipant() throws Exception {
        List<ParticipantShow> participants = getParticipants();
        Mockito.when(service.getAllParticipant()).thenReturn(participants);
        mockMvc.perform(get("/participant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].participantId",Matchers.is(1)))
                .andExpect(jsonPath("$[1].participantId",Matchers.is(2)))
                .andExpect(jsonPath("$[0].sportType",Matchers.is("ROADRACECYCLING".toUpperCase())))
                .andExpect(jsonPath("$[1].sportType",Matchers.is("formulaone".toUpperCase())))
                .andExpect(jsonPath("$[1].name",Matchers.is("Soros")));
    }

    @Test
    void addValidParticipant() throws Exception {
        ParticipantCreate participantCreate = getValidParticipant();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantCreate))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addInvalidParticipant() throws Exception {
        ParticipantCreate participantCreate = getInvalidParticipant();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantCreate))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getParticipant() throws Exception {
        ParticipantShow participantShow = getParticipants().get(0);
        Mockito.when(service.getParticipantById(participantShow.getParticipantId())).thenReturn(participantShow);
        mockMvc.perform(get("/participant/"+participantShow.getParticipantId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$.sportType", Matchers.is("roadracecycling".toUpperCase())))
                .andExpect(jsonPath("$.name",Matchers.is("Viktor")));
    }

    @Test
    void modifyValidParticipant() throws Exception {
        ParticipantCreate participantUpdateDao = getValidParticipant();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(service.updateParticipant(id,participantUpdateDao)).thenReturn(true);
        mockMvc.perform(put("/participant/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantUpdateDao))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void modifyInvalidParticipant() throws Exception {
        ParticipantCreate participantUpdateDao = getInvalidParticipant();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(service.updateParticipant(id,participantUpdateDao)).thenReturn(true);
        mockMvc.perform(put("/participant/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantUpdateDao))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyNotExistingParticipantWithValidFields() throws Exception {
        ParticipantCreate participantUpdateDao = getValidParticipant();
        int id=999;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(service.updateParticipant(id,participantUpdateDao)).thenReturn(false);
        mockMvc.perform(put("/participant/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantUpdateDao))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    void deleteParticipant() throws Exception {
        int id=1;
        mockMvc.perform(delete("/participant/"+id))
        .andExpect(status().isOk());
        Mockito.verify(service).deleteParticipantById(id);
    }
}