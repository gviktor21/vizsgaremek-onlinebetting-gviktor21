
--INSERT INTO bid_app_user VALUES ('admin','1','America',0,'1991-11-04','godov@freemail.hu','male','admin'  );
--INSERT INTO event(description,end_date,event_type,start_date) VALUES ( 'Lotto 5 sorsolas','2022-08-11',1,'2022-08-11');
INSERT INTO participant(sport_type,name) VALUES (1,'Soros Gyorgy');
-- create table bid (bid_id integer generated by default as identity, bid_amount bigint not null, bid_type varchar(255), date date, prize bigint not null, event_id integer, username varchar(255), primary key (bid_id));
--INSERT INTO bid(bid_amount,bid_type,date,prize,event_id,username) VALUES ();