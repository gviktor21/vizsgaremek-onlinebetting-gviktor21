package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.dto.SportBidCreate;
import com.gviktor.onlinebet.dto.SportBidShow;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SportBidController.class)
@ExtendWith(SpringExtension.class)
class SportBidControllerTest {

    @Autowired
    MockMvc mockMvc;

    private String url="/sportBid";
    @MockBean
    SportBidService sportBidService;

    private List<SportBidShow> getSportBids(){
        List<SportBidShow> sportBids = new ArrayList<>();
        SportBidShow sportBidShow1 = new SportBidShow();
        sportBidShow1.setBid(TestDatas.getBids().get(0));
        sportBidShow1.setParticipant(TestDatas.getParticipants().get(0));

        SportBidShow sportBidShow2 = new SportBidShow();
        sportBidShow2.setBid(TestDatas.getBids().get(1));
        sportBidShow2.setParticipant(TestDatas.getParticipants().get(1));

        sportBids.add(sportBidShow1);
        sportBids.add(sportBidShow2);
        return sportBids;
    }

    private SportBidCreate getValidSportBidCreate(){
        SportBidCreate sportBidCreate = new SportBidCreate();
        sportBidCreate.setBidId(1);
        sportBidCreate.setParticipantId(2);
        return  sportBidCreate;
    }
    private SportBidCreate getInValidSportBidCreate(){
        SportBidCreate sportBidCreate = new SportBidCreate();
        sportBidCreate.setBidId(1);
        return  sportBidCreate;
    }

    @Test
    void getAllSportBid() throws Exception {
        List<SportBidShow> sportBidShows = getSportBids();
        Mockito.when(sportBidService.getSportBids()).thenReturn(sportBidShows);
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
    void addValidSportBid() throws Exception {
        SportBidCreate validSportBid = getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(sportBidService.addSportBid(validSportBid)).thenReturn(true);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
    @Test
    void addInvalidSportBid() throws Exception {
        SportBidCreate invalidSportBid = getInValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(sportBidService.addSportBid(invalidSportBid)).thenReturn(true);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    void addNotExistingSportBidWithValidData() throws Exception {
        SportBidCreate validSportBid = getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(sportBidService.addSportBid(validSportBid)).thenReturn(false);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    void getSportBid() throws Exception {
        SportBidShow sportBidShow = getSportBids().get(0);
        int id=10;
        Mockito.when(sportBidService.getSportBidById(id)).thenReturn(sportBidShow);
        mockMvc.perform(get(url+"/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bid.bidId",Matchers.is(1)))
                .andExpect(jsonPath("$.bid.bidAmount",Matchers.is(1000)))
                .andExpect(jsonPath("$.bid.date",Matchers.is("2022-11-02")));
    }

    @Test
    void updateValidSportBid() throws Exception {
        SportBidCreate validSportBid = getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();
        int id = 1;

        Mockito.when(sportBidService.updateSportBid(id,validSportBid)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    void updateInvalidSportBid() throws Exception {
        SportBidCreate invalidSportBid = getInValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();
        int id = 100;

        Mockito.when(sportBidService.updateSportBid(id,invalidSportBid)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void updateNotExistingSportBidWithValidData() throws Exception {
        SportBidCreate validSportBid = getValidSportBidCreate();
        ObjectMapper objectMapper = new ObjectMapper();
        int id = 100;

        Mockito.when(sportBidService.updateSportBid(id,validSportBid)).thenReturn(false);

        mockMvc.perform(put(url+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSportBid))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void deleteSportBid() throws Exception {
        int id=120;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(sportBidService).deleteSportBidById(id);
    }
}