package com.gviktor.onlinebet.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.ParticipantCreateDto;
import com.gviktor.onlinebet.dto.show.ParticipantShowDto;
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

import java.util.List;

@WebMvcTest(ParticipantController.class)
@ExtendWith(SpringExtension.class)
class ParticipantControllerTest {

    @MockBean
    ParticipantService service;

    @Autowired
    MockMvc mockMvc;
    @Test
    void testGetAllParticipantReturnsListOfParticipant() throws Exception {
        List<ParticipantShowDto> participants = TestDatas.getParticipants();
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
    void testAddValidParticipant() throws Exception {
        ParticipantCreateDto participantCreateDto = TestDatas.getValidParticipant();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantCreateDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddInvalidParticipantReturnsBadeRequest() throws Exception {
        ParticipantCreateDto participantCreateDto = TestDatas.getInvalidParticipant();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/participant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantCreateDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetParticipantById() throws Exception {
        ParticipantShowDto participantShowDto = TestDatas.getParticipants().get(0);
        Mockito.when(service.getParticipantById(participantShowDto.getParticipantId())).thenReturn(participantShowDto);
        mockMvc.perform(get("/participant/"+ participantShowDto.getParticipantId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$.sportType", Matchers.is("roadracecycling".toUpperCase())))
                .andExpect(jsonPath("$.name",Matchers.is("Viktor")));
    }

    @Test
    void testModifyValidParticipant() throws Exception {
        ParticipantCreateDto participantUpdateDao = TestDatas.getValidParticipant();
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
    void testModifyInvalidParticipantReturnsBadRequest() throws Exception {
        ParticipantCreateDto participantUpdateDao = TestDatas.getInvalidParticipant();
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
    void testModifyNotExistingParticipantWithValidFieldsReturnsBadRequest() throws Exception {
        ParticipantCreateDto participantUpdateDao = TestDatas.getValidParticipant();
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
    void testDeleteParticipant() throws Exception {
        int id=1;
        mockMvc.perform(delete("/participant/"+id))
        .andExpect(status().isOk());
        Mockito.verify(service).deleteParticipantById(id);
    }
}