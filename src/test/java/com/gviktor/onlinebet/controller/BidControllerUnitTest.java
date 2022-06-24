package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.service.BidService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(BidController.class)
@ExtendWith(SpringExtension.class)
public class BidControllerUnitTest {

    @MockBean
    BidService bidService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllBids() {
    }

    @Test
    void addBid() {
    }

    @Test
    void getBidById() {
    }

    @Test
    void updateBid() {
    }

    @Test
    void deleteBidById() {
    }
}