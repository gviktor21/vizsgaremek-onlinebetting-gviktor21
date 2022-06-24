package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.BidAppUserCreate;
import com.gviktor.onlinebet.dto.BidAppUserShow;
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
    private List<BidAppUserShow> convertList(List<BidAppUser> songs){
        return  songs.stream().map(a->mapper.map(a,BidAppUserShow.class)).collect(Collectors.toList());
    }

    public List<BidAppUserShow> getAllUser(){
        return convertList(repository.findAll());
    }
    public BidAppUserShow getUserByUsername(String username){
        return mapper.map(repository.findById(username).orElseThrow(),BidAppUserShow.class);
    }

    public void addUser(BidAppUserCreate bidAppUserCreate){
        BidAppUser bidAppUser =mapper.map(bidAppUserCreate,BidAppUser.class);
        repository.save(bidAppUser);
    }
    public boolean modifyUser(String username, BidAppUserCreate bidAppUserCreate){
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
