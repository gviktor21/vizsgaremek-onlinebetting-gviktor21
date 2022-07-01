package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.dto.*;
import com.gviktor.onlinebet.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDatas {
    public static List<BidShow> getBids(){
        List<BidShow> bids = new ArrayList<>();
        BidShow bidShow1= new BidShow();
        bidShow1.setBidId(1);
        bidShow1.setBidAmount(1000);
        bidShow1.setBidType("sport");
        bidShow1.setDate(LocalDate.of(2022,11,2));
        bidShow1.setPrize(20000);
        BidShow bidShow2= new BidShow();
        bidShow2.setBidId(2);
        bidShow2.setBidAmount(330);
        bidShow2.setBidType("lotto");
        bidShow2.setDate(LocalDate.of(2022,8,2));
        bidShow2.setPrize(2000);
        bids.add(bidShow1);
        bids.add(bidShow2);
        return bids;
    }
    public static List<ParticipantShow> getParticipants(){
        List<ParticipantShow> participants = new ArrayList<>();

        ParticipantShow participantShow1 = new ParticipantShow();
        participantShow1.setParticipantId(1);
        participantShow1.setName("Viktor");
        participantShow1.setSportType(SportType.ROADRACECYCLING);

        ParticipantShow participantShow2 = new ParticipantShow();
        participantShow2.setParticipantId(2);
        participantShow2.setName("Soros");
        participantShow2.setSportType(SportType.FORMULAONE);

        participants.add(participantShow1);
        participants.add(participantShow2);
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


    public static List<BidAppUserShow> getUsers(){
        List<BidAppUserShow> userList = new ArrayList<>();
        BidAppUserShow user1 = new BidAppUserShow();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        user1.setEmail("valami@freemail.hu");
        user1.setAccountLevel(1);
        BidAppUserShow user2 = new BidAppUserShow();
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

    public static List<EventShow> getEvents(){
        List<EventShow> events = new ArrayList<>();
        EventShow eventShow1 = new EventShow();
        eventShow1.setEventId(1);
        eventShow1.setEventType(EventType.SPORT);
        eventShow1.setStartDate(LocalDate.of(1111,11,11));
        EventShow eventShow2 = new EventShow();
        eventShow2.setEventId(2);
        eventShow2.setEventType(EventType.SPORT);
        eventShow2.setStartDate(LocalDate.of(2000,10,4));
        EventShow eventShow3 = new EventShow();
        eventShow3.setEventId(3);
        eventShow3.setEventType(EventType.LOTTO5);
        eventShow3.setStartDate(LocalDate.of(2030,1,1));
        events.add(eventShow1);
        events.add(eventShow2);
        events.add(eventShow3);
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

    public static List<BidAppUserCreate> getUsersToPost(){
        List<BidAppUserCreate> userList = new ArrayList<>();
        BidAppUserCreate user1 = new BidAppUserCreate();
        user1.setUsername("viktor");
        user1.setPassword("passw");
        user1.setEmail("valami@freemail.hu");
        user1.setAccountLevel(1);
        BidAppUserCreate user2 = new BidAppUserCreate();
        user2.setPassword("passw2");
        user2.setUsername("admin");
        user2.setEmail("valami@freemail.hu");
        user2.setAccountLevel(1);
        userList.add(user1);userList.add(user2);
        return userList;
    }
    public static BidCreate getValidBid(){
        BidCreate bidCreate = new BidCreate();
        bidCreate.setBidAmount(100);
        bidCreate.setDate(LocalDate.now().plusDays(2));
        bidCreate.setPrize(2400);
        bidCreate.setBidType("Lotto");
        bidCreate.setUsername(getUsersToPost().get(0).getUsername());
        bidCreate.setEventId(1);
        return bidCreate;
    }
    static BidCreate getInvalidBid(){
        BidCreate bidCreate = new BidCreate();
        bidCreate.setBidAmount(-100);
        bidCreate.setDate(LocalDate.now().plusDays(2));
        bidCreate.setPrize(2400);
        bidCreate.setBidType("Sport");
        return bidCreate;
    }
    public static EventCreate getValidEventToPost() {
        EventCreate eventCreate = new EventCreate();
        eventCreate.setDescription("Lottohuzas");
        eventCreate.setStartDate(LocalDate.now().plusMonths(1));
        eventCreate.setEndDate(LocalDate.now().plusMonths(1));
        eventCreate.setEventType(EventType.LOTTO5);
        return eventCreate;
    }
    public static EventShow getValidEventToPostShowAsFirstEvent() {
        EventShow eventCreate = new EventShow();
        eventCreate.setEventId(1); //might couse errors
        eventCreate.setDescription("Lottohuzas");
        eventCreate.setStartDate(LocalDate.now().plusMonths(1));
        eventCreate.setEndDate(LocalDate.now().plusMonths(1));
        eventCreate.setEventType(EventType.LOTTO5);
        return eventCreate;
    }
}
