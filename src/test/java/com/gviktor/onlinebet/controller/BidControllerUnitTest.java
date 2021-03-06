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
import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.BidCreateDto;
import com.gviktor.onlinebet.dto.show.BidShowDto;
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

import java.util.List;

@WebMvcTest(BidController.class)
@ExtendWith(SpringExtension.class)
public class BidControllerUnitTest {

    @MockBean
    BidService bidService;

    @Autowired
    MockMvc mockMvc;


    @Test
    void testGetAllBidsReturnsBids() throws Exception {
        List<BidShowDto> bids= TestDatas.getBids();
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
    void testAddValidBid() throws Exception {
        BidCreateDto validBid= TestDatas.getValidBid();
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
    void testAddInvalidBidReturnsBadRequest() throws Exception {
        BidCreateDto invalidBid= TestDatas.getInvalidBid();
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
    void testGetBidById() throws Exception {
        BidShowDto bidShowDto = TestDatas.getBids().get(0);
        Mockito.when(bidService.getBidById(bidShowDto.getBidId())).thenReturn(bidShowDto);
        mockMvc.perform(get("/bid/"+ bidShowDto.getBidId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bidId",Matchers.is(1)))
                .andExpect(jsonPath("$.bidAmount",Matchers.is(1000)))
                .andExpect(jsonPath("$.date",Matchers.is("2022-11-02")));
    }

    @Test
    void testUpdateValidBid() throws Exception {
        BidCreateDto validBid= TestDatas.getValidBid();
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
    void testUpdateInvalidBidReturnBadRequest() throws Exception {
        BidCreateDto invalidBid= TestDatas.getInvalidBid();
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
    void testUpdateNoneExistentBidWithValidDataReturnsBadRequest() throws Exception {
        BidCreateDto validBid= TestDatas.getInvalidBid();
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
    void testdeleteBidById() throws Exception {
        int id=1;
        mockMvc.perform(delete("/bid/"+id)).andExpect(status().isOk());
        Mockito.verify(bidService).deleteBidById(id);
    }
}