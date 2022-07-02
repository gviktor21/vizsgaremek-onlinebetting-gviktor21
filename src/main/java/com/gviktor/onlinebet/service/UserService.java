package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.create.BidAppUserCreateDto;
import com.gviktor.onlinebet.dto.show.BidAppUserShowDto;
import com.gviktor.onlinebet.model.BidAppUser;
import com.gviktor.onlinebet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private ModelMapper mapper;
    private UserRepository repository;

    @Autowired
    public UserService(ModelMapper mapper,UserRepository repository) {
        this.mapper = mapper;
        this.repository=repository;
    }
    private List<BidAppUserShowDto> convertList(List<BidAppUser> songs){
        return  songs.stream().map(a->mapper.map(a, BidAppUserShowDto.class)).collect(Collectors.toList());
    }

    public List<BidAppUserShowDto> getAllUser(){
        return convertList(repository.findAll());
    }
    public BidAppUserShowDto getUserByUsername(String username){
        return mapper.map(repository.findById(username).orElseThrow(), BidAppUserShowDto.class);
    }

    public void addUser(BidAppUserCreateDto bidAppUserCreate){
        BidAppUser bidAppUser =mapper.map(bidAppUserCreate,BidAppUser.class);
        repository.save(bidAppUser);
    }
    public boolean modifyUser(String username, BidAppUserCreateDto bidAppUserCreate){
        BidAppUser bidAppUser =mapper.map(bidAppUserCreate,BidAppUser.class);
        if (repository.findById(username).isPresent()){
            repository.save(bidAppUser);
            return true;
        }
        return false;
    }
    public void deleteUser(String username){
        repository.deleteById(username);
    }
}
