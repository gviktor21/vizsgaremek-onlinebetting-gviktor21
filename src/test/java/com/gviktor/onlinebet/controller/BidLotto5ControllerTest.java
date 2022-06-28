package com.gviktor.onlinebet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.dto.BidLotto5Create;
import com.gviktor.onlinebet.dto.BidLotto5Show;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(BidLotto5Controller.class)
@ExtendWith(SpringExtension.class)
class BidLotto5ControllerTest {

    @Autowired
    MockMvc mockMvc;
    String url = "/bidlotto5";
    @MockBean
    BidLotto5Service bidLotto5Service;

    List<BidLotto5Show> getLottoBids(){
        List<BidLotto5Show> bidLotto5Shows = new ArrayList<>();

        BidLotto5Show bidLotto5Show1 = new BidLotto5Show();
        bidLotto5Show1.setBidId(1);
        bidLotto5Show1.setNumber1(12);
        bidLotto5Show1.setNumber2(22);
        bidLotto5Show1.setNumber3(33);
        bidLotto5Show1.setNumber4(4);
        bidLotto5Show1.setNumber5(10);

        BidLotto5Show bidLotto5Show2 = new BidLotto5Show();
        bidLotto5Show2.setBidId(2);
        bidLotto5Show2.setNumber1(11);
        bidLotto5Show2.setNumber2(45);
        bidLotto5Show2.setNumber3(5);
        bidLotto5Show2.setNumber4(4);
        bidLotto5Show2.setNumber5(28);

        bidLotto5Shows.add(bidLotto5Show1);
        bidLotto5Shows.add(bidLotto5Show2);
        return bidLotto5Shows;
    }
    BidLotto5Create getValidLotto5Bid(){
        BidLotto5Create bidLotto5Create = new BidLotto5Create();
        bidLotto5Create.setNumber1(1);
        bidLotto5Create.setNumber2(2);
        bidLotto5Create.setNumber3(4);
        bidLotto5Create.setNumber4(5);
        bidLotto5Create.setNumber5(6);
        return bidLotto5Create;
    }
    BidLotto5Create getInvalidLotto5Bid(){
        BidLotto5Create bidLotto5Create = new BidLotto5Create();
        bidLotto5Create.setNumber1(1);
        bidLotto5Create.setNumber2(2);
        bidLotto5Create.setNumber3(4);
        bidLotto5Create.setNumber4(8888);
        bidLotto5Create.setNumber5(6);
        return bidLotto5Create;
    }
    @Test
    void getAllLotto5Bids() throws Exception {
        List<BidLotto5Show> lottoBids = getLottoBids();
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
    void addValidLotto5Bid() throws Exception {
        BidLotto5Create validBidLotto5 = getValidLotto5Bid();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.addBid5Lotto(validBidLotto5)).thenReturn(true);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validBidLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addInvalidLotto5Bid() throws Exception {
        BidLotto5Create invalidBidLotto5 = getInvalidLotto5Bid();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.addBid5Lotto(invalidBidLotto5)).thenReturn(true);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void addValidLotto5BidButBidOrEventNotExists() throws Exception {
        BidLotto5Create validBidLotto5 = getValidLotto5Bid();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.addBid5Lotto(validBidLotto5)).thenReturn(false);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getLotto5ById() throws Exception {
        BidLotto5Show bidLotto5Show = getLottoBids().get(1);
        Mockito.when(bidLotto5Service.getBidLotto5ById(bidLotto5Show.getBidId())).thenReturn(bidLotto5Show);
        mockMvc.perform(get(url+"/"+bidLotto5Show.getBidId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number3",Matchers.is(5)))
                .andExpect(jsonPath("$.bidId",Matchers.is(2)))
                .andExpect(jsonPath("$.number4",Matchers.is(4)));
    }

    @Test
    void updateValidLotto5Bid() throws Exception {
        BidLotto5Create validBidLotto5 = getValidLotto5Bid();
        int id = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.updateBid5Lotto(id,validBidLotto5)).thenReturn(true);
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void updateInvalidLotto5Bid() throws Exception {
        BidLotto5Create invalidBidLotto5 = getInvalidLotto5Bid();
        int id = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.updateBid5Lotto(id,invalidBidLotto5)).thenReturn(true);
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void updateNotExistingLotto5BidWithValidData() throws Exception {
        BidLotto5Create validBidLotto5 = getValidLotto5Bid();
        int id = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(bidLotto5Service.updateBid5Lotto(id,validBidLotto5)).thenReturn(false);
        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBidLotto5))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBidLotto5ById() throws Exception {
        int id=3;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(bidLotto5Service).deleteBidLotto5ById(id);
    }
}