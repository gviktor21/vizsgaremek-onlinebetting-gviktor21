package com.gviktor.onlinebet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.dto.Lotto5Create;
import com.gviktor.onlinebet.dto.Lotto5Show;
import com.gviktor.onlinebet.service.Lotto5Service;
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


@WebMvcTest(Lotto5Controller.class)
@ExtendWith(SpringExtension.class)
class Lotto5ControllerTest {

    @MockBean
    Lotto5Service lotto5Service;

    @Autowired
    MockMvc mockMvc;
    private String url ="/lotto5";

    private List<Lotto5Show> getLotto5results(){
        List<Lotto5Show> lotto5List = new ArrayList<>();

        Lotto5Show lotto5Show1 =new Lotto5Show();
        lotto5Show1.setEventId(1);
        lotto5Show1.setNumber1(31);
        lotto5Show1.setNumber2(11);
        lotto5Show1.setNumber3(2);
        lotto5Show1.setNumber4(9);
        lotto5Show1.setNumber5(55);

        Lotto5Show lotto5Show2 =new Lotto5Show();
        lotto5Show2.setEventId(2);
        lotto5Show2.setNumber1(31);
        lotto5Show2.setNumber2(11);
        lotto5Show2.setNumber3(2);
        lotto5Show2.setNumber4(9);
        lotto5Show2.setNumber5(55);

        lotto5List.add(lotto5Show1);
        lotto5List.add(lotto5Show2);
        return lotto5List;
    }

    private Lotto5Create getValidLotto5Create(){
     Lotto5Create lotto5Create = new Lotto5Create();
     lotto5Create.setEventId(1);
     lotto5Create.setNumber1(61);
     lotto5Create.setNumber2(55);
     lotto5Create.setNumber3(72);
     lotto5Create.setNumber4(26);
     lotto5Create.setNumber5(4);
     return lotto5Create;

    }
    private Lotto5Create getInvalidLotto5Create(){
        Lotto5Create lotto5Create = new Lotto5Create();
        lotto5Create.setEventId(1);
        lotto5Create.setNumber1(61);
        lotto5Create.setNumber2(666);
        lotto5Create.setNumber3(72);
        lotto5Create.setNumber4(26);
        lotto5Create.setNumber5(4);
        return lotto5Create;
    }

    @Test
    void getAllLotto5Beds() throws Exception{
        List<Lotto5Show> lotto5Results= getLotto5results();
        Mockito.when(lotto5Service.getAllLotto5()).thenReturn(lotto5Results);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].number1",Matchers.is(31)))
                .andExpect(jsonPath("$[0].eventId",Matchers.is(1)))
                .andExpect(jsonPath("$[0].number4",Matchers.is(9)))
                .andExpect(jsonPath("$[1].number2",Matchers.is(11)))
                .andExpect(jsonPath("$[1].eventId",Matchers.is(2)))
                .andExpect(jsonPath("$[1].number5",Matchers.is(55)));
    }

    @Test
    void getLotto5EventResult() throws Exception {
        Lotto5Show lotto5Show = getLotto5results().get(0);
        Mockito.when(lotto5Service.getLotto5ById(lotto5Show.getEventId())).thenReturn(lotto5Show);

        mockMvc.perform(get(url+"/"+lotto5Show.getEventId()))
                .andExpect(jsonPath("$.number2",Matchers.is(11)))
                .andExpect(jsonPath("$.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$.number3",Matchers.is(2)))
                .andExpect(status().isOk());
    }

    @Test
    void addValidLotto5Result() throws Exception {
        Lotto5Create validLotto5 = getValidLotto5Create();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.addLotto5Result(validLotto5)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addInvalidLotto5Result() throws Exception {
        Lotto5Create invalidLotto5 = getInvalidLotto5Create();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.addLotto5Result(invalidLotto5)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddValidLotto5ResultForNonExistingEvent() throws Exception{
        Lotto5Create validLotto5 = getValidLotto5Create();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.addLotto5Result(validLotto5)).thenReturn(false);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyValidLotto5Result() throws Exception {
        Lotto5Create validLotto5 = getValidLotto5Create();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.updateLotto5Result(id,validLotto5)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void modifyInvalidLotto5Result() throws Exception {
        Lotto5Create validLotto5 = getInvalidLotto5Create();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.updateLotto5Result(id,validLotto5)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyValidLotto5ResultForNonExistingEvent() throws Exception {
        Lotto5Create validLotto5 = getValidLotto5Create();
        int id=33;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.updateLotto5Result(id,validLotto5)).thenReturn(false);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteLotto5Result() throws Exception {
        int id=1;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(lotto5Service).deleteLotto5ResultById(id);
    }
}