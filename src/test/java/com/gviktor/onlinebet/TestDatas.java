package com.gviktor.onlinebet;

import com.gviktor.onlinebet.dto.create.*;
import com.gviktor.onlinebet.dto.show.*;
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
    public static BidAppUserCreateDto getNewValidUser(){
        BidAppUserCreateDto newUser = new BidAppUserCreateDto();
        newUser.setPassword("password");
        newUser.setUsername("username");
        newUser.setEmail("valami@gmail.com");
        newUser.setAccountLevel(1);
        return newUser;
    }
    public static BidAppUserCreateDto getNewInValidUser(){
        BidAppUserCreateDto newUser = new BidAppUserCreateDto();
        newUser.setPassword("pass");
        newUser.setEmail("valami@gmail.com");
        return newUser;
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
    public static EventCreateDto getValidEvent(){
        EventCreateDto eventCreateDto = new EventCreateDto();
        eventCreateDto.setEventType(EventType.LOTTO5);
        eventCreateDto.setDescription("Draw numbers of Lotto 5");
        eventCreateDto.setStartDate(LocalDate.now());
        eventCreateDto.setEndDate(LocalDate.now().plusDays(2));
        return eventCreateDto;
    }
    public static EventCreateDto getInvalidEvent(){
        EventCreateDto eventCreateDto = new EventCreateDto();
        eventCreateDto.setEventType(EventType.LOTTO5);
        eventCreateDto.setDescription("Lotto".repeat(256));
        eventCreateDto.setStartDate(LocalDate.now());
        eventCreateDto.setEndDate(LocalDate.now().plusDays(2));
        return eventCreateDto;
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
    public static BidCreateDto getInvalidBid(){
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
    public static List<BidLotto5ShowDto> getLottoBids(){
        List<BidLotto5ShowDto> bidLotto5ShowDtos = new ArrayList<>();

        BidLotto5ShowDto bidLotto5ShowDto1 = new BidLotto5ShowDto();
        bidLotto5ShowDto1.setBidId(1);
        bidLotto5ShowDto1.setNumber1(12);
        bidLotto5ShowDto1.setNumber2(22);
        bidLotto5ShowDto1.setNumber3(33);
        bidLotto5ShowDto1.setNumber4(4);
        bidLotto5ShowDto1.setNumber5(10);

        BidLotto5ShowDto bidLotto5ShowDto2 = new BidLotto5ShowDto();
        bidLotto5ShowDto2.setBidId(2);
        bidLotto5ShowDto2.setNumber1(11);
        bidLotto5ShowDto2.setNumber2(45);
        bidLotto5ShowDto2.setNumber3(5);
        bidLotto5ShowDto2.setNumber4(4);
        bidLotto5ShowDto2.setNumber5(28);

        bidLotto5ShowDtos.add(bidLotto5ShowDto1);
        bidLotto5ShowDtos.add(bidLotto5ShowDto2);
        return bidLotto5ShowDtos;
    }
    public static BidLotto5CreateDto getValidLotto5Bid(){
        BidLotto5CreateDto bidLotto5CreateDto = new BidLotto5CreateDto();
        bidLotto5CreateDto.setNumber1(1);
        bidLotto5CreateDto.setNumber2(2);
        bidLotto5CreateDto.setNumber3(4);
        bidLotto5CreateDto.setNumber4(5);
        bidLotto5CreateDto.setNumber5(6);
        return bidLotto5CreateDto;
    }
    public static BidLotto5CreateDto getInvalidLotto5Bid(){
        BidLotto5CreateDto bidLotto5CreateDto = new BidLotto5CreateDto();
        bidLotto5CreateDto.setNumber1(1);
        bidLotto5CreateDto.setNumber2(2);
        bidLotto5CreateDto.setNumber3(4);
        bidLotto5CreateDto.setNumber4(8888);
        bidLotto5CreateDto.setNumber5(6);
        return bidLotto5CreateDto;
    }
    public static List<Lotto5ShowDto> getLotto5results(){
        List<Lotto5ShowDto> lotto5List = new ArrayList<>();

        Lotto5ShowDto lotto5ShowDto1 =new Lotto5ShowDto();
        lotto5ShowDto1.setEventId(1);
        lotto5ShowDto1.setNumber1(31);
        lotto5ShowDto1.setNumber2(11);
        lotto5ShowDto1.setNumber3(2);
        lotto5ShowDto1.setNumber4(9);
        lotto5ShowDto1.setNumber5(55);

        Lotto5ShowDto lotto5ShowDto2 =new Lotto5ShowDto();
        lotto5ShowDto2.setEventId(2);
        lotto5ShowDto2.setNumber1(31);
        lotto5ShowDto2.setNumber2(11);
        lotto5ShowDto2.setNumber3(2);
        lotto5ShowDto2.setNumber4(9);
        lotto5ShowDto2.setNumber5(55);

        lotto5List.add(lotto5ShowDto1);
        lotto5List.add(lotto5ShowDto2);
        return lotto5List;
    }

    public static Lotto5CreateDto getValidLotto5Create(){
        Lotto5CreateDto lotto5CreateDto = new Lotto5CreateDto();
        lotto5CreateDto.setEventId(1);
        lotto5CreateDto.setNumber1(61);
        lotto5CreateDto.setNumber2(55);
        lotto5CreateDto.setNumber3(72);
        lotto5CreateDto.setNumber4(26);
        lotto5CreateDto.setNumber5(4);
        return lotto5CreateDto;

    }
    public static Lotto5CreateDto getInvalidLotto5Create(){
        Lotto5CreateDto lotto5CreateDto = new Lotto5CreateDto();
        lotto5CreateDto.setEventId(1);
        lotto5CreateDto.setNumber1(61);
        lotto5CreateDto.setNumber2(666);
        lotto5CreateDto.setNumber3(72);
        lotto5CreateDto.setNumber4(26);
        lotto5CreateDto.setNumber5(4);
        return lotto5CreateDto;
    }
    public static ParticipantCreateDto getValidParticipant(){
        ParticipantCreateDto participantCreateDto = new ParticipantCreateDto();
        participantCreateDto.setName("Viktor");
        participantCreateDto.setSportType(SportType.FORMULAONE);
        return participantCreateDto;
    }
    public static ParticipantCreateDto getInvalidParticipant(){
        ParticipantCreateDto participantCreateDto = new ParticipantCreateDto();
        participantCreateDto.setName("");
        participantCreateDto.setSportType(SportType.FORMULAONE);
        return participantCreateDto;
    }
    public static List<SportBidShowDto> getSportBids(){
        List<SportBidShowDto> sportBids = new ArrayList<>();
        SportBidShowDto sportBidShowDto1 = new SportBidShowDto();
        sportBidShowDto1.setBid(TestDatas.getBids().get(0));
        sportBidShowDto1.setParticipant(TestDatas.getParticipants().get(0));

        SportBidShowDto sportBidShowDto2 = new SportBidShowDto();
        sportBidShowDto2.setBid(TestDatas.getBids().get(1));
        sportBidShowDto2.setParticipant(TestDatas.getParticipants().get(1));

        sportBids.add(sportBidShowDto1);
        sportBids.add(sportBidShowDto2);
        return sportBids;
    }

    public static SportBidCreateDto getValidSportBidCreate(){
        SportBidCreateDto sportBidCreateDto = new SportBidCreateDto();
        sportBidCreateDto.setBidId(1);
        sportBidCreateDto.setParticipantId(2);
        return sportBidCreateDto;
    }
    public static SportBidCreateDto getInValidSportBidCreate(){
        SportBidCreateDto sportBidCreateDto = new SportBidCreateDto();
        sportBidCreateDto.setBidId(1);
        return sportBidCreateDto;
    }
    public static List<SportEventShowDto> getSportEvents(){
        List<SportEventShowDto> sportEvents = new ArrayList<>();
        SportEventShowDto sportEvent1 = new SportEventShowDto();
        sportEvent1.setEventId(1);
        sportEvent1.setSportType(SportType.FORMULAONE);
        sportEvent1.setWinner(TestDatas.getParticipants().get(0));

        SportEventShowDto sportEvent2 = new SportEventShowDto();
        sportEvent2.setEventId(2);
        sportEvent2.setSportType(SportType.FOOTBALL);
        sportEvent2.setWinner(TestDatas.getParticipants().get(1));
        sportEvents.add(sportEvent1);
        sportEvents.add(sportEvent2);
        return  sportEvents;
    }
    public static SportEventCreateDto getValidSportEvent(){
        SportEventCreateDto sportEventCreateDto = new SportEventCreateDto();
        sportEventCreateDto.setEventId(1);
        sportEventCreateDto.setSportType(SportType.FOOTBALL);
        sportEventCreateDto.setWinnerId(1);
        return sportEventCreateDto;
    }

    public static SportEventCreateDto getInvalidSportEvent(){
        SportEventCreateDto sportEventCreateDto = new SportEventCreateDto();
        sportEventCreateDto.setSportType(SportType.FOOTBALL);
        sportEventCreateDto.setWinnerId(1);
        return sportEventCreateDto;
    }
    public static List<SportParticipantShowDto> getSportParticipants(){
        List<SportParticipantShowDto> sportParticipantShowDtos = new ArrayList<>();
        SportParticipantShowDto sportParticipant1 = new SportParticipantShowDto();
        sportParticipant1.setParticipant(TestDatas.getParticipants().get(0));
        sportParticipant1.setEvent(TestDatas.getEvents().get(0));
        SportParticipantShowDto sportParticipant2 = new SportParticipantShowDto();
        sportParticipant2.setParticipant(TestDatas.getParticipants().get(1));
        sportParticipant2.setEvent(TestDatas.getEvents().get(1));
        sportParticipantShowDtos.add(sportParticipant1);
        sportParticipantShowDtos.add(sportParticipant2);
        return sportParticipantShowDtos;
    }

    public static SportParticipantCreateDto getValidSportParticipant(){
        SportParticipantCreateDto sportParticipantCreateDto = new SportParticipantCreateDto();
        sportParticipantCreateDto.setParticipantId(1);
        sportParticipantCreateDto.setMultiplier(10);
        sportParticipantCreateDto.setEventId(2);
        return sportParticipantCreateDto;
    }
    public static SportParticipantCreateDto getInvalidSportParticipant(){
        SportParticipantCreateDto sportParticipantCreateDto = new SportParticipantCreateDto();
        sportParticipantCreateDto.setParticipantId(1);
        sportParticipantCreateDto.setMultiplier(-10);
        sportParticipantCreateDto.setEventId(2);
        return sportParticipantCreateDto;
    }
}
