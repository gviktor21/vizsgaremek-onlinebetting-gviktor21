package com.gviktor.onlinebet;

import com.gviktor.onlinebet.dto.create.BidAppUserCreateDto;
import com.gviktor.onlinebet.dto.create.BidCreateDto;
import com.gviktor.onlinebet.dto.create.EventCreateDto;
import com.gviktor.onlinebet.dto.show.BidAppUserShowDto;
import com.gviktor.onlinebet.dto.show.BidShowDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeneralBetIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    private String url="/bid";
    private String urlEvent="/event";
    private String urlUser="/user";


    private void postATestEvent(){
        EventCreateDto eventCreateDto = TestDatas.getValidEventToPost();
        postEvent(eventCreateDto,urlEvent);
    }
    private void postPastEvent() {
        EventCreateDto eventCreateDto = TestDatas.getValidEventToPost();
        eventCreateDto.setStartDate(LocalDate.now().minusDays(10));
        postEvent(eventCreateDto,urlEvent);

    }
    private void postUserWithMoney(){
        BidAppUserCreateDto bidAppUserCreateDto =TestDatas.getUsersToPost().get(0);
        bidAppUserCreateDto.setBalance(1000);
        postBidUsers(bidAppUserCreateDto,urlUser);
    }

    private List<BidCreateDto> getPostedBids(){
        List<BidCreateDto> bidsToPost= new ArrayList<>();
        BidCreateDto bidCreateDto1 = TestDatas.getValidBid();

        BidCreateDto bidCreateDto2 = new BidCreateDto();
        bidCreateDto2.setBidAmount(50);
        bidCreateDto2.setBidType(bidCreateDto1.getBidType());
        bidCreateDto2.setEventId(bidCreateDto1.getEventId());
        bidCreateDto2.setDate(bidCreateDto1.getDate().minusDays(2));
        bidCreateDto2.setPrize(200);
        bidCreateDto2.setUsername(bidCreateDto1.getUsername());

        bidsToPost.add(bidCreateDto1);
        bidsToPost.add(bidCreateDto2);
        return bidsToPost;
    }
    private BidCreateDto getUpdatedBidData(){
        BidCreateDto bidCreateDto = getPostedBids().get(0);
        bidCreateDto.setBidAmount(99);
        bidCreateDto.setPrize(990);
        bidCreateDto.setBidType("Updated");
        return bidCreateDto;
    }
    private List<BidShowDto> getExpectedBids(){
        List<BidShowDto> expectedBids= new ArrayList<>();

        BidAppUserShowDto bidAppUserShowDto = TestDatas.getUsers().get(0);
        bidAppUserShowDto.setBalance(850);

        BidShowDto bid1 = new BidShowDto();
        bid1.setBidId(1);
        bid1.setUser(bidAppUserShowDto);
        bid1.setBidEvent(TestDatas.getValidEventToPostShowAsFirstEvent());
        bid1.setBidAmount(getPostedBids().get(0).getBidAmount());
        bid1.setBidType(getPostedBids().get(0).getBidType());
        bid1.setDate(getPostedBids().get(0).getDate());
        bid1.setPrize(getPostedBids().get(0).getPrize());

        BidShowDto bid2 = new BidShowDto();
        bid2.setBidId(2);
        bid2.setBidAmount(50);
        bid2.setBidType(bid1.getBidType());
        bid2.setBidEvent(bid1.getBidEvent());
        bid2.setBidEvent(bid1.getBidEvent());
        bid2.setUser(bid1.getUser());
        bid2.setDate(bid1.getDate().minusDays(2));
        bid2.setPrize(getPostedBids().get(1).getPrize());

        expectedBids.add(bid1);
        expectedBids.add(bid2);
        return expectedBids;
    }

    @Test
    @Order(1)
    public void getAllBid(){
        postATestEvent();
        postUserWithMoney();
        List<BidCreateDto> postedBids = getPostedBids();
        for (BidCreateDto postedBid : postedBids) {
            postBid(postedBid,url);
        }

        ResponseEntity<BidShowDto[]> responseEntity = testRestTemplate.getForEntity(url, BidShowDto[].class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        hasTheSameElements(responseEntity.getBody());
    }

    @Test
    @Order(2)
    public void getBidById(){
        String getUrl =url+"/"+getExpectedBids().get(1).getBidId();
        ResponseEntity<BidShowDto> responseEntity = testRestTemplate.getForEntity(getUrl, BidShowDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(getExpectedBids().get(1),responseEntity.getBody());
    }
    @Test
    @Order(3)
    public void addBidIfUserHaveInSufficientMoneyThenReturnBadRequest(){
        BidCreateDto expensiveBid = getPostedBids().get(0);
        expensiveBid.setBidAmount(TestDatas.getUsers().get(0).getBalance() +100000);
        HttpEntity<BidCreateDto> httpEntity = createHttpEntity(expensiveBid);
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(url,HttpMethod.POST,httpEntity,Void.class);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test
    @Order(4)
    public void addBidForPastEventReturnsBadRequest(){
        postPastEvent();
        BidCreateDto bidToNewEvent = getPostedBids().get(0);
        bidToNewEvent.setEventId(2);
        HttpEntity<BidCreateDto> httpEntity = createHttpEntity(bidToNewEvent);
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(url,HttpMethod.POST,httpEntity,Void.class);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }


    @Test
    @Order(5)
    public void testUpdateBid(){
        BidCreateDto updatedBidData = getUpdatedBidData();
        String putUrl =url+"/"+getExpectedBids().get(0).getBidId();

        HttpEntity<BidCreateDto> httpEntity = createHttpEntity(updatedBidData);
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(putUrl,HttpMethod.PUT,httpEntity,Void.class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

        ResponseEntity<BidShowDto> responseEntityResult= testRestTemplate.getForEntity(putUrl, BidShowDto.class);
        BidShowDto actual = responseEntityResult.getBody();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(updatedBidData.getBidType(),actual.getBidType());
        assertEquals(updatedBidData.getBidAmount(),actual.getBidAmount());
        assertEquals(updatedBidData.getPrize(),actual.getPrize());
        assertEquals(getExpectedBids().get(0).getUser(),actual.getUser());
        assertEquals(getExpectedBids().get(0).getBidEvent(),actual.getBidEvent());

    }

    @Test
    @Order(6)
    public void testDeleteBidById(){
        ResponseEntity<BidShowDto[]> responseEntity = testRestTemplate.getForEntity(url, BidShowDto[].class);
        int expectedSize=responseEntity.getBody().length;

        testRestTemplate.delete(url+"/"+getExpectedBids().get(0).getBidId());
        responseEntity = testRestTemplate.getForEntity(url, BidShowDto[].class);
        assertEquals(--expectedSize,responseEntity.getBody().length);

        testRestTemplate.delete(url+"/"+getExpectedBids().get(1).getBidId());
        responseEntity = testRestTemplate.getForEntity(url, BidShowDto[].class);
        assertEquals(--expectedSize,responseEntity.getBody().length);
    }

    private void postBid(BidCreateDto bid, String url){
        HttpEntity<BidCreateDto> httpEntity = createHttpEntity(bid);
        testRestTemplate.postForEntity(url,httpEntity,String.class).getStatusCode();
    }
    private void postEvent(EventCreateDto eventCreateDto, String url){
        HttpEntity<EventCreateDto> httpEntity = createHttpEntity(eventCreateDto);
        testRestTemplate.postForEntity(url,httpEntity,String.class).getStatusCode();
    }
    private HttpEntity<EventCreateDto> createHttpEntity(EventCreateDto event){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(event,httpHeaders);
    }

    private HttpEntity<BidCreateDto> createHttpEntity(BidCreateDto bid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(bid,httpHeaders);
    }
    private void postBidUsers(BidAppUserCreateDto bidAppUserCreate, String url){
        HttpEntity<BidAppUserCreateDto> httpEntity = createHttpEntity(bidAppUserCreate);
        testRestTemplate.postForEntity(url,httpEntity,String.class).getStatusCode();
    }

    private HttpEntity<BidAppUserCreateDto> createHttpEntity(BidAppUserCreateDto bidAppUserCreate){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(bidAppUserCreate,httpHeaders);
    }
    private void hasTheSameElements(BidShowDto[] expectedBid){
        List<BidShowDto> listExpected = getExpectedBids();
        List<BidShowDto> listActual = Arrays.asList(expectedBid);
        assertTrue(listActual.size()== listExpected.size() && listExpected.containsAll(listActual) && listActual.containsAll(listExpected));
    }
}
