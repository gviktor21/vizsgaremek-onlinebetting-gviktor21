package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gviktor.onlinebet.dto.BidCreate;
import com.gviktor.onlinebet.dto.BidShow;
import com.gviktor.onlinebet.service.BidService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(BidController.class)
@ExtendWith(SpringExtension.class)
public class BidControllerUnitTest {

    @MockBean
    BidService bidService;

    @Autowired
    MockMvc mockMvc;

    private List<BidShow> getBids(){
        List<BidShow> bids = new ArrayList<>();
        BidShow bidShow1= new BidShow();
        bidShow1.setBidId(1);
        bidShow1.setBidAmount(1000);
        bidShow1.setBidType("sport");
        bidShow1.setDate(LocalDate.of(2022,11,2));
        bidShow1.setPrize(20000);
        BidShow bidShow2= new BidShow();
        bidShow2.setBidId(2);
        bidShow2.setBidAmount(330);
        bidShow2.setBidType("lotto");
        bidShow2.setDate(LocalDate.of(2022,8,2));
        bidShow2.setPrize(2000);
        bids.add(bidShow1);
        bids.add(bidShow2);
        return bids;
    }

    private BidCreate getValidBid(){
        BidCreate bidCreate = new BidCreate();
        bidCreate.setBidAmount(100);
        bidCreate.setDate(LocalDate.now().plusDays(2));
        bidCreate.setPrize(2400);
        bidCreate.setBidType("Sport");
        bidCreate.setUsername("valaki");
        bidCreate.setEventId(1);
        return bidCreate;
    }
    private BidCreate getInvalidBid(){
        BidCreate bidCreate = new BidCreate();
        bidCreate.setBidAmount(-100);
        bidCreate.setDate(LocalDate.now().plusDays(2));
        bidCreate.setPrize(2400);
        bidCreate.setBidType("Sport");
        return bidCreate;
    }
    @Test
    void getAllBids() throws Exception {
        List<BidShow> bids= getBids();
        Mockito.when(bidService.getAllBids()).thenReturn(bids);
        mockMvc.perform(get("/bid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].bidId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].bidAmount",Matchers.is(1000)))
                .andExpect(jsonPath("$[0].date",Matchers.is("2022-11-02")))
                .andExpect(jsonPath("$[1].bidId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].bidAmount",Matchers.is(330)))
                .andExpect(jsonPath("$[1].date",Matchers.is("2022-08-02")))
                .andExpect(jsonPath("$[1].bidType",Matchers.is("lotto")));
    }

    @Test
    void addValidBid() throws Exception {
        BidCreate validBid= getValidBid();
        Mockito.when(bidService.addBid(validBid)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(post("/bid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validBid))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void addInvalidBid() throws Exception {
        BidCreate invalidBid= getInvalidBid();
        Mockito.when(bidService.addBid(invalidBid)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(post("/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBid))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getBidById() throws Exception {
        BidShow bidShow = getBids().get(0);
        Mockito.when(bidService.getBidById(bidShow.getBidId())).thenReturn(bidShow);
        mockMvc.perform(get("/bid/"+bidShow.getBidId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bidId",Matchers.is(1)))
                .andExpect(jsonPath("$.bidAmount",Matchers.is(1000)))
                .andExpect(jsonPath("$.date",Matchers.is("2022-11-02")));
    }

    @Test
    void updateValidBid() throws Exception {
        BidCreate validBid= getValidBid();
        int id = 1;
        Mockito.when(bidService.updateBid(id,validBid)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(put("/bid/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBid))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void updateInvalidBid() throws Exception {
        BidCreate invalidBid= getInvalidBid();
        int id = 1;
        Mockito.when(bidService.updateBid(id,invalidBid)).thenReturn(true);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(put("/bid/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBid))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateNoneExistentBidWithValidData() throws Exception {
        BidCreate validBid= getInvalidBid();
        int id = 444;
        Mockito.when(bidService.updateBid(id,validBid)).thenReturn(false);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mockMvc.perform(put("/bid/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBid))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBidById() throws Exception {
        int id=1;
        mockMvc.perform(delete("/bid/"+id)).andExpect(status().isOk());
        Mockito.verify(bidService).deleteBidById(id);
    }
}