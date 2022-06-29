package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.controller.TestDatas;
import com.gviktor.onlinebet.dto.BidAppUserShow;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;

    @Test
    void getAllUser() {
        Mockito.when(userRepository.findAll()).thenReturn(TestDatas.getUsersEntity());
        List<BidAppUserShow> list = userService.getAllUser();
        assertEquals(2,list.size());
        Mockito.verify(userRepository,Mockito.times(1)).findAll();
    }

    @Test
    void getUserByUsername() {
        BidAppUserShow bidAppUserShow = TestDatas.getUsers().get(0);
        String username = bidAppUserShow.getUsername();
        Optional<BidAppUser> unMappedUser= Optional.of(TestDatas.getUsersEntity().get(0));
        Mockito.when(userRepository.findById(username)).thenReturn(unMappedUser);
        Mockito.when(modelMapper.map(unMappedUser.get(), BidAppUserShow.class)).thenReturn(bidAppUserShow);
        //test
        BidAppUserShow result = userService.getUserByUsername(username);
        assertEquals(bidAppUserShow.getUsername(),result.getUsername());
        Mockito.verify(userRepository,Mockito.times(1)).findById(username);
    }

    @Test
    void addUser() {
    }

    @Test
    void modifyUser() {
    }

    @Test
    void deleteUser() {
        String id="id";
        userService.deleteUser(id);
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(id);
    }
}