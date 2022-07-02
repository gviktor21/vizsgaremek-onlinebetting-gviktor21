package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.create.Lotto5CreateDto;
import com.gviktor.onlinebet.dto.show.Lotto5ShowDto;
import com.gviktor.onlinebet.model.Event;
import com.gviktor.onlinebet.model.EventType;
import com.gviktor.onlinebet.model.Lotto5;
import com.gviktor.onlinebet.repository.EventRepository;
import com.gviktor.onlinebet.repository.Lotto5Repository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Lotto5Service {

    private Lotto5Repository repository;
    private EventRepository eventRepository;
    private ModelMapper mapper;

    @Autowired
    public Lotto5Service(Lotto5Repository repository, EventRepository eventRepository, ModelMapper mapper) {
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }
    private List<Lotto5ShowDto> convertList(List<Lotto5> lotto5Events){
        return  lotto5Events.stream().map(a->mapper.map(a, Lotto5ShowDto.class)).collect(Collectors.toList());
    }

    public List<Lotto5ShowDto> getAllLotto5() {
        return convertList(repository.findAll());
    }

    public Lotto5ShowDto getLotto5ById(int id) {
        return mapper.map(repository.findById(id), Lotto5ShowDto.class);
    }


    public boolean addLotto5Result(Lotto5CreateDto lotto5CreateDto) {
        Optional<Event> event = eventRepository.findById(lotto5CreateDto.getEventId());
        if(event.isPresent() && event.get().getEventType()== EventType.LOTTO5 && testNumbers(lotto5CreateDto)){
            repository.save(mapper.map(lotto5CreateDto,Lotto5.class));
            return true;
        }
        return false;
    }
    private boolean testNumbers(Lotto5CreateDto bidLotto5CreateDto){
        Set<Integer> numbers = new HashSet<>();
        int lottoType=5;
        numbers.add(bidLotto5CreateDto.getNumber1());
        numbers.add(bidLotto5CreateDto.getNumber2());
        numbers.add(bidLotto5CreateDto.getNumber3());
        numbers.add(bidLotto5CreateDto.getNumber4());
        numbers.add(bidLotto5CreateDto.getNumber5());
        return numbers.size()==lottoType;
    }

    public boolean updateLotto5Result(int id, Lotto5CreateDto lotto5CreateDto) {
        Optional<Event> event = eventRepository.findById(lotto5CreateDto.getEventId());
        if(event.isPresent() && event.get().getEventType()== EventType.LOTTO5 && testNumbers(lotto5CreateDto)){
            lotto5CreateDto.setEventId(id);
            repository.save(mapper.map(lotto5CreateDto,Lotto5.class));
            return true;
        }
        return false;
    }

    public void deleteLotto5ResultById(int id) {
        repository.deleteById(id);
    }
}
