package com.gviktor.onlinebet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gviktor.onlinebet.TestDatas;
import com.gviktor.onlinebet.dto.create.Lotto5CreateDto;
import com.gviktor.onlinebet.dto.show.Lotto5ShowDto;
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

import java.util.List;


@WebMvcTest(Lotto5Controller.class)
@ExtendWith(SpringExtension.class)
class Lotto5ControllerTest {

    @MockBean
    Lotto5Service lotto5Service;

    @Autowired
    MockMvc mockMvc;
    private String url ="/lotto5";

    @Test
    void testGetAllLotto5ReturnLotto5Events() throws Exception{
        List<Lotto5ShowDto> lotto5Results= TestDatas.getLotto5results();
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
    void testGetLotto5EventResultById() throws Exception {
        Lotto5ShowDto lotto5ShowDto = TestDatas.getLotto5results().get(0);
        Mockito.when(lotto5Service.getLotto5ById(lotto5ShowDto.getEventId())).thenReturn(lotto5ShowDto);

        mockMvc.perform(get(url+"/"+ lotto5ShowDto.getEventId()))
                .andExpect(jsonPath("$.number2",Matchers.is(11)))
                .andExpect(jsonPath("$.eventId",Matchers.is(1)))
                .andExpect(jsonPath("$.number3",Matchers.is(2)))
                .andExpect(status().isOk());
    }

    @Test
    void testAddValidLotto5Result() throws Exception {
        Lotto5CreateDto validLotto5 = TestDatas.getValidLotto5Create();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.addLotto5Result(validLotto5)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddInvalidLotto5ResultReturnsBadRequest() throws Exception {
        Lotto5CreateDto invalidLotto5 = TestDatas.getInvalidLotto5Create();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.addLotto5Result(invalidLotto5)).thenReturn(true);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddValidLotto5ResultForNonExistingEventReturnsBadRequest() throws Exception{
        Lotto5CreateDto validLotto5 = TestDatas.getValidLotto5Create();
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.addLotto5Result(validLotto5)).thenReturn(false);

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModifyValidLotto5Result() throws Exception {
        Lotto5CreateDto validLotto5 = TestDatas.getValidLotto5Create();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.updateLotto5Result(id,validLotto5)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testmodifyInvalidLotto5ResultReturnsBadRequest() throws Exception {
        Lotto5CreateDto validLotto5 = TestDatas.getInvalidLotto5Create();
        int id=1;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.updateLotto5Result(id,validLotto5)).thenReturn(true);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModifyValidLotto5ResultForNonExistingEventReturnsBadRequest() throws Exception {
        Lotto5CreateDto validLotto5 = TestDatas.getValidLotto5Create();
        int id=33;
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.when(lotto5Service.updateLotto5Result(id,validLotto5)).thenReturn(false);

        mockMvc.perform(put(url+"/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLotto5))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteLotto5ResultById() throws Exception {
        int id=1;
        mockMvc.perform(delete(url+"/"+id)).andExpect(status().isOk());
        Mockito.verify(lotto5Service).deleteLotto5ResultById(id);
    }
}