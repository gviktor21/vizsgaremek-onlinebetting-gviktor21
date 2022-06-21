package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.EventShow;
import com.gviktor.onlinebet.dto.Lotto5Show;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.repository.Lotto5Repository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Lotto5Service {

    private Lotto5Repository repository;
    private ModelMapper mapper;

    @Autowired
    public Lotto5Service(Lotto5Repository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    private List<Lotto5Show> convertList(List<Event> lotto5Events){
        return  lotto5Events.stream().map(a->mapper.map(a, Lotto5Show.class)).collect(Collectors.toList());
    }
}
