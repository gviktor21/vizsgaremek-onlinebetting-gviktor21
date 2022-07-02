package com.gviktor.onlinebet;

import com.gviktor.onlinebet.controller.TestDatas;
import com.gviktor.onlinebet.dto.create.BidAppUserCreateDto;
import com.gviktor.onlinebet.dto.show.BidAppUserShowDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

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

        ResponseEntity<BidAppUserShowDto[]> responseEntity = testRestTemplate.getForEntity(url, BidAppUserShowDto[].class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

        hasTheSameElements(responseEntity.getBody());
    }

    @Test
    public void testGetById(){
        addUsers();
        ResponseEntity<BidAppUserShowDto> responseEntity = testRestTemplate.getForEntity(url+"/"+TestDatas.getUsersToPost().get(0).getUsername(), BidAppUserShowDto.class);
        BidAppUserShowDto result = responseEntity.getBody();
        BidAppUserShowDto expected = TestDatas.getUsers().get(0);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(result.getPassword(),expected.getPassword());
        assertEquals(result.getUsername(),expected.getUsername());
    }

    @Test
    public void testDeleteUserById(){
        addUsers();
        testRestTemplate.delete(url+"/"+TestDatas.getUsersToPost().get(0).getUsername());
        ResponseEntity<BidAppUserShowDto[]> responseEntity = testRestTemplate.getForEntity(url, BidAppUserShowDto[].class);
        assertEquals(responseEntity.getBody().length,1);
        testRestTemplate.delete(url+"/"+TestDatas.getUsersToPost().get(1).getUsername());
        responseEntity = testRestTemplate.getForEntity(url, BidAppUserShowDto[].class);
        assertEquals(responseEntity.getBody().length,0);
    }
    @Test
    public void testDeleteNotExistingUserNoExceptionDrop(){
        addUsers();
        testRestTemplate.delete(url+"/"+"whatever");
        ResponseEntity<BidAppUserShowDto[]> responseEntity = testRestTemplate.getForEntity(url, BidAppUserShowDto[].class);
        assertEquals(responseEntity.getBody().length,2);
    }
    @Test
    public void testUpdateExistingUserReturnStatus200(){
        addUsers();

        BidAppUserCreateDto userToUpdate = updateTestUser();

        HttpEntity<BidAppUserCreateDto> httpEntity = createHttpEntity(userToUpdate);
        String putUrl =url+"/"+userToUpdate.getUsername();
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(putUrl,HttpMethod.PUT,httpEntity,Void.class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        //test
        BidAppUserShowDto result = testRestTemplate.getForEntity(putUrl, BidAppUserShowDto.class).getBody();
        assertEquals(result.getPassword(),userToUpdate.getPassword());
        assertEquals(result.getAccountLevel(),userToUpdate.getAccountLevel());
        assertEquals(result.getBalance(),userToUpdate.getBalance());
    }

    private BidAppUserCreateDto updateTestUser() {
        BidAppUserCreateDto userToUpdate= TestDatas.getUsersToPost().get(0);
        userToUpdate.setPassword("newpassw");
        userToUpdate.setAccountLevel(2);
        userToUpdate.setBalance(100);
        return userToUpdate;
    }
    //exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType)

    @Test
    public void testUpdateNotExistingUserReturnBadRequest(){
        addUsers();

        BidAppUserCreateDto userToUpdate = updateTestUser();
        userToUpdate.setUsername("whatever");
        HttpEntity<BidAppUserCreateDto> httpEntity = createHttpEntity(userToUpdate);
        String putUrl =url+"/"+userToUpdate.getUsername();
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(putUrl,HttpMethod.PUT,httpEntity,Void.class);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

    }
    @Test
    public void testUpdateExistingUserNotLetUsernameChanged(){
        addUsers();

        BidAppUserCreateDto userToUpdate = updateTestUser();
        String putUrl =url+"/"+userToUpdate.getUsername();
        userToUpdate.setUsername("whatever");
        HttpEntity<BidAppUserCreateDto> httpEntity = createHttpEntity(userToUpdate);
        ResponseEntity<Void> responseEntity= testRestTemplate.exchange(putUrl,HttpMethod.PUT,httpEntity,Void.class);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
    private void addUsers() {
        List<BidAppUserCreateDto> usersToPost= TestDatas.getUsersToPost();

        for (BidAppUserCreateDto bidAppUserCreate : usersToPost) {
            postBidUsers(bidAppUserCreate,url);
        }
    }

    private void postBidUsers(BidAppUserCreateDto bidAppUserCreate, String url){
        HttpEntity<BidAppUserCreateDto> httpEntity = createHttpEntity(bidAppUserCreate);
        System.out.println(testRestTemplate.postForEntity(url,httpEntity,String.class).getStatusCode());
    }

    private HttpEntity<BidAppUserCreateDto> createHttpEntity(BidAppUserCreateDto bidAppUserCreateDto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(bidAppUserCreateDto,httpHeaders);
    }

    private void hasTheSameElements(BidAppUserShowDto[] expectedUser){
        List<BidAppUserShowDto> listExpected = TestDatas.getUsers();
        List<BidAppUserShowDto> listActual = Arrays.asList(expectedUser);
        System.out.println(listExpected.size() +" "+listActual.size() );
        assertTrue(listActual.size()== listExpected.size() && listExpected.containsAll(listActual) && listActual.containsAll(listExpected));
    }

}
