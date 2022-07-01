package com.gviktor.onlinebet.service;

import com.gviktor.onlinebet.dto.Lotto5Create;
import com.gviktor.onlinebet.dto.Lotto5Show;
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
    private List<Lotto5Show> convertList(List<Lotto5> lotto5Events){
        return  lotto5Events.stream().map(a->mapper.map(a, Lotto5Show.class)).collect(Collectors.toList());
    }

    public List<Lotto5Show> getAllLotto5() {
        return convertList(repository.findAll());
    }

    public Lotto5Show getLotto5ById(int id) {
        return mapper.map(repository.findById(id),Lotto5Show.class);
    }


    public boolean addLotto5Result(Lotto5Create lotto5Create) {
        Optional<Event> event = eventRepository.findById(lotto5Create.getEventId());
        if(event.isPresent() && event.get().getEventType()== EventType.LOTTO5 && testNumbers(lotto5Create)){
            repository.save(mapper.map(lotto5Create,Lotto5.class));
            return true;
        }
        return false;
    }
    private boolean testNumbers(Lotto5Create bidLotto5Create){
        Set<Integer> numbers = new HashSet<>();
        int lottoType=5;
        numbers.add(bidLotto5Create.getNumber1());
        numbers.add(bidLotto5Create.getNumber2());
        numbers.add(bidLotto5Create.getNumber3());
        numbers.add(bidLotto5Create.getNumber4());
        numbers.add(bidLotto5Create.getNumber5());
        return numbers.size()==lottoType;
    }

    public boolean updateLotto5Result(int id, Lotto5Create lotto5Create) {
        Optional<Event> event = eventRepository.findById(lotto5Create.getEventId());
        if(event.isPresent() && event.get().getEventType()== EventType.LOTTO5 && testNumbers(lotto5Create)){
            lotto5Create.setEventId(id);
            repository.save(mapper.map(lotto5Create,Lotto5.class));
            return true;
        }
        return false;
    }

    public void deleteLotto5ResultById(int id) {
        repository.deleteById(id);
    }
}
