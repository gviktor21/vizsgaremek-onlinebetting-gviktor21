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

    @Test
    public void testGetAll(){
        addUsers();

        ResponseEntity<BidAppUserShow[]> responseEntity = testRestTemplate.getForEntity(url,BidAppUserShow[].class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

        hasTheSameElements(responseEntity.getBody());
    }

    @Test
    public void testGetById(){
        addUsers();
        ResponseEntity<BidAppUserShow> responseEntity = testRestTemplate.getForEntity(url+"/"+TestDatas.getUsersToPost().get(0).getUsername(),BidAppUserShow.class);
        BidAppUserShow result = responseEntity.getBody();
        BidAppUserShow expected = TestDatas.getUsers().get(0);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(result.getPassword(),expected.getPassword());
        assertEquals(result.getUsername(),expected.getUsername());
    }

    @Test
    public void testDeleteUserById(){
        addUsers();
        testRestTemplate.delete(url+"/"+TestDatas.getUsersToPost().get(0).getUsername());
        ResponseEntity<BidAppUserShow[]> responseEntity = testRestTemplate.getForEntity(url,BidAppUserShow[].class);
        assertEquals(responseEntity.getBody().length,1);
        testRestTemplate.delete(url+"/"+TestDatas.getUsersToPost().get(1).getUsername());
        responseEntity = testRestTemplate.getForEntity(url,BidAppUserShow[].class);
        assertEquals(responseEntity.getBody().length,0);
    }
    @Test
    public void testDeleteNotExistingUserNoExceptionDrop(){
        addUsers();
        testRestTemplate.delete(url+"/"+"whatever");
        ResponseEntity<BidAppUserShow[]> responseEntity = testRestTemplate.getForEntity(url,BidAppUserShow[].class);
        assertEquals(responseEntity.getBody().length,2);
    }
    @Test
    public void testUpdateExistingUserReturnStatus200(){
        addUsers();

        BidAppUserCreate userToUpdate = updateTestUser();

        HttpEntity<BidAppUserCreate> httpEntity = createHttpEntity(userToUpdate);
        String putUrl =url+"/"+userToUpdate.getUsername();
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(putUrl,HttpMethod.PUT,httpEntity,Void.class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        //test
        BidAppUserShow result = testRestTemplate.getForEntity(putUrl,BidAppUserShow.class).getBody();
        assertEquals(result.getPassword(),userToUpdate.getPassword());
        assertEquals(result.getAccountLevel(),userToUpdate.getAccountLevel());
        assertEquals(result.getBalance(),userToUpdate.getBalance());
    }

    private BidAppUserCreate updateTestUser() {
        BidAppUserCreate userToUpdate= TestDatas.getUsersToPost().get(0);
        userToUpdate.setPassword("newpassw");
        userToUpdate.setAccountLevel(2);
        userToUpdate.setBalance(100);
        return userToUpdate;
    }
    //exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType)

    @Test
    public void testUpdateNotExistingUserReturnBadRequest(){
        addUsers();

        BidAppUserCreate userToUpdate = updateTestUser();
        userToUpdate.setUsername("whatever");
        HttpEntity<BidAppUserCreate> httpEntity = createHttpEntity(userToUpdate);
        String putUrl =url+"/"+userToUpdate.getUsername();
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(putUrl,HttpMethod.PUT,httpEntity,Void.class);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

    }
    @Test
    public void testUpdateExistingUserNotLetUsernameChanged(){
        addUsers();

        BidAppUserCreate userToUpdate = updateTestUser();
        String putUrl =url+"/"+userToUpdate.getUsername();
        userToUpdate.setUsername("whatever");
        HttpEntity<BidAppUserCreate> httpEntity = createHttpEntity(userToUpdate);
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(putUrl,HttpMethod.PUT,httpEntity,Void.class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
    private void addUsers() {
        List<BidAppUserCreate> usersToPost= TestDatas.getUsersToPost();

        for (BidAppUserCreate bidAppUserCreate : usersToPost) {
            postBidUsers(bidAppUserCreate,url);
        }
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

}
