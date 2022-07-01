drop table if exists sport_bid CASCADE;
drop table if exists sport_event CASCADE;
drop table if exists sport_participant CASCADE;


create table sport_bid
(
    sport_bid_id   integer not null,
    bid_id         integer,
    participant_id integer,
    primary key (sport_bid_id)
);
create table sport_event
(
    event_id   integer not null,
    sport_type integer,
    winner_id  integer,
    primary key (event_id)
);
create table sport_participant
(
    id             integer not null,
    multiplier     integer not null,
    event_id       integer,
    participant_id integer,
    primary key (id)
);

alter table sport_bid
    add constraint fk_sportBid_bid foreign key (bid_id) references bid;
alter table sport_bid
    add constraint fk_BidTo_participant foreign key (participant_id) references participant;
alter table sport_event
    add constraint fk_sport_event_winner foreign key (winner_id) references sport_participant;
alter table sport_participant
    add constraint fk_sportEvent_sportParticipant foreign key (event_id) references sport_event;
alter table sport_participant
    add constraint fk_sportEvent_participant foreign key (participant_id) references participant;