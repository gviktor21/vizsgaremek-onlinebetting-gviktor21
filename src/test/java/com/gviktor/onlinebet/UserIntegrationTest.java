package com.gviktor.onlinebet;

import com.gviktor.onlinebet.controller.TestDatas;
import com.gviktor.onlinebet.dto.BidAppUserCreate;
import com.gviktor.onlinebet.dto.BidAppUserShow;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    private String url="/user";

    private List<BidAppUserCreate> getUsersToPost(){
        List<BidAppUserCreate> userList = new ArrayList<>();
        BidAppUserCreate user1 = new BidAppUserCreate();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        user1.setEmail("valami@freemail.hu");
        user1.setAccountLevel(1);
        BidAppUserCreate user2 = new BidAppUserCreate();
        user2.setPassword("passw2");
        user2.setUsername("admin");
        user2.setEmail("valami@freemail.hu");
        user2.setAccountLevel(1);
        userList.add(user1);userList.add(user2);
        return userList;
    }
    @Test
    public void testGetAll(){
        List<BidAppUserCreate> usersToPost= getUsersToPost();

        for (BidAppUserCreate bidAppUserCreate : usersToPost) {
            postBidUsers(bidAppUserCreate,url);
        }
        
        ResponseEntity<BidAppUserShow[]> responseEntity = testRestTemplate.getForEntity(url,BidAppUserShow[].class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

        hasTheSameElements(responseEntity.getBody());
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

    private void hasTheSameElements(BidAppUserShow[] expectedUser){
        List<BidAppUserShow> listExpected = TestDatas.getUsers();
        List<BidAppUserShow> listActual = Arrays.asList(expectedUser);
        System.out.println(listExpected.size() +" "+listActual.size() );
        assertTrue(listActual.size()== listExpected.size() && listExpected.containsAll(listActual) && listActual.containsAll(listExpected));
    }

    @Test
    void contextLoads(){

    }
}
