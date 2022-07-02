package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.SportBidCreateDto;
import com.gviktor.onlinebet.dto.show.SportBidShowDto;
import com.gviktor.onlinebet.service.SportBidService;
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

@WebMvcTest(SportBidController.class)
@ExtendWith(SpringExtension.class)
class SportBidControllerTest {

    @Autowired
    MockMvc mockMvc;

    private String url="/sportBid";
    @MockBean
    SportBidService sportBidService;


    @Test
    void testGetAllSportBid() throws Exception {
        List<SportBidShowDto> sportBidShowDtos = TestDatas.getSportBids();
        Mockito.when(sportBidService.getSportBids()).thenReturn(sportBidShowDtos);
        mockMvc.perform(get(url))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].bid.bidId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].bid.bidAmount",Matchers.is(1000)))
                .andExpect(jsonPath("$[0].bid.date",Matchers.is("2022-11-02")))
                .andExpect(jsonPath("$[0].participant.participantId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].participant.sportType",Matchers.is("ROADRACECYCLING".toUpperCase())))
                .andExpect(jsonPath("$[1].bid.date",Matchers.is("2022-08-02")))
                .andExpect(jsonPath("$[1].participant.participantId",Matchers.is(2)))
                .andExpect(status().isOk());

    }

    @Test
    void testAddValidSportBid() throws Exception {
        SportBidCreateDto validSportBid = TestDatas.getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(sportBidService.addSportBid(validSportBid)).thenReturn(true);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
    @Test
    void testAddInvalidSportBidReturnsBadRequest() throws Exception {
        SportBidCreateDto invalidSportBid = TestDatas.getInValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(sportBidService.addSportBid(invalidSportBid)).thenReturn(true);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void testAddNotExistingSportBidWithValidDataReturnsBadRequest() throws Exception {
        SportBidCreateDto validSportBid = TestDatas.getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(sportBidService.addSportBid(validSportBid)).thenReturn(false);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    void testGetSportBidById() throws Exception {
        SportBidShowDto sportBidShowDto = TestDatas.getSportBids().get(0);
        int id=10;
        Mockito.when(sportBidService.getSportBidById(id)).thenReturn(sportBidShowDto);
        mockMvc.perform(get(url+"/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bid.bidId",Matchers.is(1)))
                .andExpect(jsonPath("$.bid.bidAmount",Matchers.is(1000)))
                .andExpect(jsonPath("$.bid.date",Matchers.is("2022-11-02")));
    }

    @Test
    void testUpdateValidSportBid() throws Exception {
        SportBidCreateDto validSportBid = TestDatas.getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();
        int id = 1;

        Mockito.when(sportBidService.updateSportBid(id,validSportBid)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    void testUpdateInvalidSportBidReturnsBadRequest() throws Exception {
        SportBidCreateDto invalidSportBid = TestDatas.getInValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();
        int id = 100;

        Mockito.when(sportBidService.updateSportBid(id,invalidSportBid)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateNotExistingSportBidWithValidDataReturnsBadRequest() throws Exception {
        SportBidCreateDto validSportBid = TestDatas.getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();
        int id = 100;

        Mockito.when(sportBidService.updateSportBid(id,validSportBid)).thenReturn(false);

        mockMvc.perform(put(url+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteSportBidById() throws Exception {
        int id=120;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(sportBidService).deleteSportBidById(id);
    }
}