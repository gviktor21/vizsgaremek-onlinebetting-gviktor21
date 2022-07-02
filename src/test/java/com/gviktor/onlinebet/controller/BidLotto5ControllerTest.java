package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.BidLotto5CreateDto;
import com.gviktor.onlinebet.dto.show.BidLotto5ShowDto;
import com.gviktor.onlinebet.service.BidLotto5Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.hamcrest.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(BidLotto5Controller.class)
@ExtendWith(SpringExtension.class)
class BidLotto5ControllerTest {

    @Autowired
    MockMvc mockMvc;
    String url = "/bidlotto5";
    @MockBean
    BidLotto5Service bidLotto5Service;


    @Test
    void testGetAllLotto5BidsReturnBids() throws Exception {
        List<BidLotto5ShowDto> lottoBids = TestDatas.getLottoBids();
        Mockito.when(bidLotto5Service.getAllLotto5Bids()).thenReturn(lottoBids);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].number1",Matchers.is(12)))
                .andExpect(jsonPath("$[0].bidId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].number4",Matchers.is(4)))
                .andExpect(jsonPath("$[1].number2",Matchers.is(45)))
                .andExpect(jsonPath("$[1].bidId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].number5",Matchers.is(28)));

    }

    @Test
    void testAddValidLotto5Bid() throws Exception {
        BidLotto5CreateDto validBidLotto5 = TestDatas.getValidLotto5Bid();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.addBid5Lotto(validBidLotto5)).thenReturn(true);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validBidLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddInvalidLotto5BidReturnsBadRequest() throws Exception {
        BidLotto5CreateDto invalidBidLotto5 = TestDatas.getInvalidLotto5Bid();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.addBid5Lotto(invalidBidLotto5)).thenReturn(true);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testAddValidLotto5BidButBidOrEventNotExistsReturnsBadRequest() throws Exception {
        BidLotto5CreateDto validBidLotto5 = TestDatas.getValidLotto5Bid();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.addBid5Lotto(validBidLotto5)).thenReturn(false);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testGetLotto5ById() throws Exception {
        BidLotto5ShowDto bidLotto5ShowDto = TestDatas.getLottoBids().get(1);
        Mockito.when(bidLotto5Service.getBidLotto5ById(bidLotto5ShowDto.getBidId())).thenReturn(bidLotto5ShowDto);
        mockMvc.perform(get(url+"/"+ bidLotto5ShowDto.getBidId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number3",Matchers.is(5)))
                .andExpect(jsonPath("$.bidId",Matchers.is(2)))
                .andExpect(jsonPath("$.number4",Matchers.is(4)));
    }

    @Test
    void testUpdateValidLotto5Bid() throws Exception {
        BidLotto5CreateDto validBidLotto5 = TestDatas.getValidLotto5Bid();
        int id = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.updateBid5Lotto(id,validBidLotto5)).thenReturn(true);
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testUpdateInvalidLotto5BidReturnsBadRequest() throws Exception {
        BidLotto5CreateDto invalidBidLotto5 = TestDatas.getInvalidLotto5Bid();
        int id = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.updateBid5Lotto(id,invalidBidLotto5)).thenReturn(true);
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testUpdateNotExistingLotto5BidWithValidDataReturnsBadRequest() throws Exception {
        BidLotto5CreateDto validBidLotto5 = TestDatas.getValidLotto5Bid();
        int id = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.updateBid5Lotto(id,validBidLotto5)).thenReturn(false);
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testdeleteBidLotto5ById() throws Exception {
        int id=3;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(bidLotto5Service).deleteBidLotto5ById(id);
    }
}