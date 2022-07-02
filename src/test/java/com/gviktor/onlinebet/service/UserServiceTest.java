package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.controller.TestDatas;
import com.gviktor.onlinebet.dto.create.BidAppUserCreateDto;
import com.gviktor.onlinebet.dto.show.BidAppUserShowDto;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
        List<BidAppUserShowDto> list = userService.getAllUser();
        assertEquals(2,list.size());
        Mockito.verify(userRepository,Mockito.times(1)).findAll();
    }

    @Test
    void getUserByUsername() {
        BidAppUserShowDto bidAppUserShowDto = TestDatas.getUsers().get(0);
        String username = bidAppUserShowDto.getUsername();
        Optional<BidAppUser> unMappedUser= Optional.of(TestDatas.getUsersEntity().get(0));
        Mockito.when(userRepository.findById(username)).thenReturn(unMappedUser);
        Mockito.when(modelMapper.map(unMappedUser.get(), BidAppUserShowDto.class)).thenReturn(bidAppUserShowDto);
        //test
        BidAppUserShowDto result = userService.getUserByUsername(username);
        assertEquals(bidAppUserShowDto.getUsername(),result.getUsername());
        Mockito.verify(userRepository,Mockito.times(1)).findById(username);
    }

    @Test
    void addUser() {
        BidAppUserCreateDto bidAppUserCreate = new BidAppUserCreateDto();
        BidAppUser savedEntity = new BidAppUser();
        Mockito.when(modelMapper.map(bidAppUserCreate,BidAppUser.class)).thenReturn(savedEntity);

        userService.addUser(bidAppUserCreate);
        Mockito.verify(userRepository,Mockito.times(1)).save(savedEntity);
    }

    @Test
    void modifyExistingUser() {
        BidAppUserCreateDto bidAppUserCreate = new BidAppUserCreateDto();
        BidAppUser savedEntity = new BidAppUser();
        String username= "Gyuribacsi";
        Mockito.when(userRepository.findById(username)).thenReturn(Optional.of(savedEntity));
        Mockito.when(modelMapper.map(bidAppUserCreate,BidAppUser.class)).thenReturn(savedEntity);

        assertTrue(userService.modifyUser(username,bidAppUserCreate));
        Mockito.verify(userRepository,Mockito.times(1)).save(savedEntity);
    }
    @Test
    void modifyNonExistingUser() {
        BidAppUserCreateDto bidAppUserCreate = new BidAppUserCreateDto();
        BidAppUser savedEntity = new BidAppUser();
        String username= "Gyuribacsi";
        Mockito.when(userRepository.findById(username)).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(bidAppUserCreate,BidAppUser.class)).thenReturn(savedEntity);

        assertFalse(userService.modifyUser(username,bidAppUserCreate));
        Mockito.verify(userRepository,Mockito.times(0)).save(savedEntity);
    }
    @Test
    void deleteUser() {
        String id="id";
        userService.deleteUser(id);
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(id);
    }
}