package com.gviktor.onlinebet;

import com.gviktor.onlinebet.controller.TestDatas;
import com.gviktor.onlinebet.dto.*;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.model.EventType;
import org.junit.jupiter.api.Test;
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
public class GeneralBetIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    private String url="/bid";
    private String urlEvent="/event";
    private String urlUser="/user";


    private void postATestEvent(){
        EventCreate eventCreate = TestDatas.getValidEventToPost();
        postEvent(eventCreate,urlEvent);
    }


    private void postUserWithMoney(){
        BidAppUserCreate bidAppUserCreate =TestDatas.getUsersToPost().get(0);
        bidAppUserCreate.setBalance(1000);
        postBidUsers(bidAppUserCreate,urlUser);
    }

    private List<BidCreate> getPostedBids(){
        List<BidCreate> bidsToPost= new ArrayList<>();
        BidCreate bidCreate1 = TestDatas.getValidBid();

        BidCreate bidCreate2 = new BidCreate();
        bidCreate2.setBidAmount(50);
        bidCreate2.setBidType(bidCreate1.getBidType());
        bidCreate2.setEventId(bidCreate1.getEventId());
        bidCreate2.setDate(bidCreate1.getDate().minusDays(2));
        bidCreate2.setPrize(200);
        bidCreate2.setUsername(bidCreate1.getUsername());
        bidsToPost.add(bidCreate1);
        bidsToPost.add(bidCreate2);
        return bidsToPost;
    }
    private List<BidShow> getExpectedBids(){
        List<BidShow> expectedBids= new ArrayList<>();
        BidAppUserShow bidAppUserShow = TestDatas.getUsers().get(0);
        bidAppUserShow.setBalance(1000);
        BidShow bid1 = new BidShow();
        bid1.setBidId(1);
        bid1.setUser(bidAppUserShow);
        bid1.setBidEvent(TestDatas.getValidEventToPostShowAsFirstEvent());
        bid1.setBidAmount(getPostedBids().get(0).getBidAmount());
        bid1.setBidType(getPostedBids().get(0).getBidType());
        bid1.setDate(getPostedBids().get(0).getDate());
        bid1.setPrize(getPostedBids().get(0).getPrize());

        BidShow bid2 = new BidShow();
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
    public void getAllBid(){
        postATestEvent();
        postUserWithMoney();
        List<BidCreate> postedBids = getPostedBids();
        for (BidCreate postedBid : postedBids) {
            postBid(postedBid,url);
        }
        ResponseEntity<BidShow[]> responseEntity = testRestTemplate.getForEntity(url,BidShow[].class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        hasTheSameElements(responseEntity.getBody());
    }

    private void postBid(BidCreate bid, String url){
        HttpEntity<BidCreate> httpEntity = createHttpEntity(bid);
        System.out.println(testRestTemplate.postForEntity(url,httpEntity,String.class).getStatusCode());
    }
    private void postEvent(EventCreate eventCreate, String url){
        HttpEntity<EventCreate> httpEntity = createHttpEntity(eventCreate);
        System.out.println(testRestTemplate.postForEntity(url,httpEntity,String.class).getStatusCode());
    }
    private HttpEntity<EventCreate> createHttpEntity(EventCreate event){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(event,httpHeaders);
    }

    private HttpEntity<BidCreate> createHttpEntity(BidCreate bid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(bid,httpHeaders);
    }
    private void postBidUsers(BidAppUserCreate bidAppUserCreate,String url){
        HttpEntity<BidAppUserCreate> httpEntity = createHttpEntity(bidAppUserCreate);
        System.out.println(testRestTemplate.postForEntity(url,httpEntity,String.class).getStatusCode());
    }

    private HttpEntity<BidAppUserCreate> createHttpEntity(BidAppUserCreate bidAppUserCreate){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(bidAppUserCreate,httpHeaders);
    }
    private void hasTheSameElements(BidShow[] expectedBid){
        List<BidShow> listExpected = getExpectedBids();
        List<BidShow> listActual = Arrays.asList(expectedBid);
        listActual.forEach(System.out::println);
        System.out.println("-----------------------------------------------------------");
        listExpected.forEach(System.out::println);
        assertTrue(listActual.size()== listExpected.size() && listExpected.containsAll(listActual) && listActual.containsAll(listExpected));
    }
}
