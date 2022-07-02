package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.create.BidAppUserCreateDto;
import com.gviktor.onlinebet.dto.create.BidCreateDto;
import com.gviktor.onlinebet.dto.create.EventCreateDto;
import com.gviktor.onlinebet.dto.show.BidAppUserShowDto;
import com.gviktor.onlinebet.dto.show.BidShowDto;
import com.gviktor.onlinebet.dto.show.EventShowDto;
import com.gviktor.onlinebet.dto.show.ParticipantShowDto;
import com.gviktor.onlinebet.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDatas {
    public static List<BidShowDto> getBids(){
        List<BidShowDto> bids = new ArrayList<>();
        BidShowDto bidShowDto1 = new BidShowDto();
        bidShowDto1.setBidId(1);
        bidShowDto1.setBidAmount(1000);
        bidShowDto1.setBidType("sport");
        bidShowDto1.setDate(LocalDate.of(2022,11,2));
        bidShowDto1.setPrize(20000);
        BidShowDto bidShowDto2 = new BidShowDto();
        bidShowDto2.setBidId(2);
        bidShowDto2.setBidAmount(330);
        bidShowDto2.setBidType("lotto");
        bidShowDto2.setDate(LocalDate.of(2022,8,2));
        bidShowDto2.setPrize(2000);
        bids.add(bidShowDto1);
        bids.add(bidShowDto2);
        return bids;
    }
    public static List<ParticipantShowDto> getParticipants(){
        List<ParticipantShowDto> participants = new ArrayList<>();

        ParticipantShowDto participantShowDto1 = new ParticipantShowDto();
        participantShowDto1.setParticipantId(1);
        participantShowDto1.setName("Viktor");
        participantShowDto1.setSportType(SportType.ROADRACECYCLING);

        ParticipantShowDto participantShowDto2 = new ParticipantShowDto();
        participantShowDto2.setParticipantId(2);
        participantShowDto2.setName("Soros");
        participantShowDto2.setSportType(SportType.FORMULAONE);

        participants.add(participantShowDto1);
        participants.add(participantShowDto2);
        return participants;
    }
    public static List<Participant> getParticipantEntities(){
        List<Participant> participants = new ArrayList<>();

        Participant participantShow1 = new Participant();
        participantShow1.setParticipantId(1);
        participantShow1.setName("Viktor");
        participantShow1.setSportType(SportType.ROADRACECYCLING);

        Participant participantShow2 = new Participant();
        participantShow2.setParticipantId(2);
        participantShow2.setName("Soros");
        participantShow2.setSportType(SportType.FORMULAONE);

        participants.add(participantShow1);
        participants.add(participantShow2);
        return participants;
    }


    public static List<BidAppUserShowDto> getUsers(){
        List<BidAppUserShowDto> userList = new ArrayList<>();
        BidAppUserShowDto user1 = new BidAppUserShowDto();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        user1.setEmail("valami@freemail.hu");
        user1.setAccountLevel(1);
        BidAppUserShowDto user2 = new BidAppUserShowDto();
        user2.setPassword("passw2");
        user2.setUsername("admin");
        user2.setEmail("valami@freemail.hu");
        user2.setAccountLevel(1);

        userList.add(user1);userList.add(user2);
        return userList;
    }
    public static List<BidAppUser> getUsersEntity(){
        List<BidAppUser> userList = new ArrayList<>();
        BidAppUser user1 = new BidAppUser();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        user1.setEmail("valami@freemail.hu");
        user1.setAccountLevel(1);
        BidAppUser user2 = new BidAppUser();
        user2.setPassword("passw2");
        user2.setUsername("admin");
        user2.setEmail("valami@freemail.hu");
        user2.setAccountLevel(1);
        userList.add(user1);userList.add(user2);
        return userList;
    }

    public static List<EventShowDto> getEvents(){
        List<EventShowDto> events = new ArrayList<>();
        EventShowDto eventShowDto1 = new EventShowDto();
        eventShowDto1.setEventId(1);
        eventShowDto1.setEventType(EventType.SPORT);
        eventShowDto1.setStartDate(LocalDate.of(1111,11,11));
        EventShowDto eventShowDto2 = new EventShowDto();
        eventShowDto2.setEventId(2);
        eventShowDto2.setEventType(EventType.SPORT);
        eventShowDto2.setStartDate(LocalDate.of(2000,10,4));
        EventShowDto eventShowDto3 = new EventShowDto();
        eventShowDto3.setEventId(3);
        eventShowDto3.setEventType(EventType.LOTTO5);
        eventShowDto3.setStartDate(LocalDate.of(2030,1,1));
        events.add(eventShowDto1);
        events.add(eventShowDto2);
        events.add(eventShowDto3);
        return events;
    }
    public static List<Event>getEventEntities(){
        List<Event> events = new ArrayList<>();
        Event eventShow1 = new Event();
        eventShow1.setEventId(1);
        eventShow1.setEventType(EventType.SPORT);
        eventShow1.setStartDate(LocalDate.of(1111,11,11));
        Event eventShow2 = new Event();
        eventShow2.setEventId(2);
        eventShow2.setEventType(EventType.SPORT);
        eventShow2.setStartDate(LocalDate.of(2000,10,4));
        Event eventShow3 = new Event();
        eventShow3.setEventId(3);
        eventShow3.setEventType(EventType.LOTTO5);
        eventShow3.setStartDate(LocalDate.of(2030,1,1));
        events.add(eventShow1);
        events.add(eventShow2);
        events.add(eventShow3);
        return events;
    }

    public static List<BidAppUserCreateDto> getUsersToPost(){
        List<BidAppUserCreateDto> userList = new ArrayList<>();
        BidAppUserCreateDto user1 = new BidAppUserCreateDto();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        user1.setEmail("valami@freemail.hu");
        user1.setAccountLevel(1);
        BidAppUserCreateDto user2 = new BidAppUserCreateDto();
        user2.setPassword("passw2");
        user2.setUsername("admin");
        user2.setEmail("valami@freemail.hu");
        user2.setAccountLevel(1);
        userList.add(user1);userList.add(user2);
        return userList;
    }
    public static BidCreateDto getValidBid(){
        BidCreateDto bidCreateDto = new BidCreateDto();
        bidCreateDto.setBidAmount(100);
        bidCreateDto.setDate(LocalDate.now().plusDays(2));
        bidCreateDto.setPrize(2400);
        bidCreateDto.setBidType("Lotto");
        bidCreateDto.setUsername(getUsersToPost().get(0).getUsername());
        bidCreateDto.setEventId(1);
        return bidCreateDto;
    }
    static BidCreateDto getInvalidBid(){
        BidCreateDto bidCreateDto = new BidCreateDto();
        bidCreateDto.setBidAmount(-100);
        bidCreateDto.setDate(LocalDate.now().plusDays(2));
        bidCreateDto.setPrize(2400);
        bidCreateDto.setBidType("Sport");
        return bidCreateDto;
    }
    public static EventCreateDto getValidEventToPost() {
        EventCreateDto eventCreateDto = new EventCreateDto();
        eventCreateDto.setDescription("Lottohuzas");
        eventCreateDto.setStartDate(LocalDate.now().plusMonths(1));
        eventCreateDto.setEndDate(LocalDate.now().plusMonths(1));
        eventCreateDto.setEventType(EventType.LOTTO5);
        return eventCreateDto;
    }
    public static EventShowDto getValidEventToPostShowAsFirstEvent() {
        EventShowDto eventCreate = new EventShowDto();
        eventCreate.setEventId(1); //might couse errors
        eventCreate.setDescription("Lottohuzas");
        eventCreate.setStartDate(LocalDate.now().plusMonths(1));
        eventCreate.setEndDate(LocalDate.now().plusMonths(1));
        eventCreate.setEventType(EventType.LOTTO5);
        return eventCreate;
    }
}
