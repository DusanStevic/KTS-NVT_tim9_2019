-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Ovo su sve imena kolona u tabelama db(Column anotacija u klasama), a ne imena polja u klasama

INSERT INTO authorities (id, role) VALUES (1, 'ROLE_REGISTERED_USER');
INSERT INTO authorities (id, role) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authorities (id, role) VALUES (3, 'ROLE_SYS_ADMIN');


-- password is 'user' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'user', 1, 'user', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Bob', 'Bobic', 'bob@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false);
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'user', 4, 'user2', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Arpad', 'Varga Somodji', 'tirmann25+10@gmail.com', true, '2019-04-18 20:58:00', '0658845455', false);
-- password is 'admin' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'admin', 2, 'admin', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Tom', 'Tomic', 'tom@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false);
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'sys', 3, 'sys', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Jovan', 'Jovanovic', 'tirmann25@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false);


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1); -- registered user has ROLE_REGISTERED_USER
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 1); -- registered user has ROLE_REGISTERED_USER
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2); -- adminstrator has ROLE_ADMIN
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 3); -- system adminstrator has ROLE_SYS_ADMIN

INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (1, "Street1", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, true);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (2, "Street2", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (3, "Street3", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (4, "Street4", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (5, "Street5", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (6, "Street6", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (7, "Street7", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (8, "Street8", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, true);
INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (9, "Street9", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);

INSERT INTO locations (id, name, description, deleted, address, latitude, longitude) values (1, "SPENS NS", "Biggest location for sports in Vojvodina", "1970-01-01 00:00:00", "Street 2 Novi Sad Serbia", 45.267136, 19.833549); 
INSERT INTO locations (id, name, description, deleted, address, latitude, longitude) values (2, "SPENS NS deleted", "Biggest location for sports in Vojvodina", "2019-11-26 00:00:00", "Street 3 Novi Sad Serbia", 45.267136, 19.833549);--deleted
INSERT INTO locations (id, name, description, deleted, address, latitude, longitude) values (3, "SPENS NS update", "Biggest location for sports in Vojvodina", "1970-01-01 00:00:00", "Street 4 Novi Sad Serbia", 45.267136, 19.833549);
INSERT INTO locations (id, name, description, deleted, address, latitude, longitude) values (4, "SPENS NS to be deleted", "Biggest location for sports in Vojvodina", "1970-01-01 00:00:00", "Street 5 Novi Sad Serbia", 45.267136, 19.833549);
INSERT INTO locations (id, name, description, deleted, address, latitude, longitude) values (5, "SPENS NS to be deleted2", "Biggest location for sports in Vojvodina", "1970-01-01 00:00:00", "Street 6 Novi Sad Serbia", 45.267136, 19.833549);

INSERT INTO halls (id, name, location_id, deleted) values (1, "Main Hall", 1, false);
INSERT INTO halls (id, name, location_id, deleted) values (2, "Main Hall deleted", 1, true);
INSERT INTO halls (id, name, location_id, deleted) values (3, "Main Hall update", 1, false);
INSERT INTO halls (id, name, location_id, deleted) values (4, "Main Hall to be deleted", 3, false);
INSERT INTO halls (id, name, location_id, deleted) values (5, "Main Hall to be deleted2", 3, false);

INSERT INTO sectors (sector_type, id , name, capacity, hall_id, deleted) VALUES ('standing', 1, 'Stand', 500, 1, false);	--standing sector
INSERT INTO sectors (sector_type, id , name, capacity, hall_id, deleted) VALUES ('standing', 2, 'Stand deleted', 500, 1, true);	--standing sector
INSERT INTO sectors (sector_type, id , name, capacity, hall_id, deleted) VALUES ('standing', 3, 'Stand update', 1000, 1, false); --standing sector

INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id, deleted) VALUES ('sitting', 4, 'Sit', 10, 9, 1, false); --sitting sector
INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id, deleted) VALUES ('sitting', 5, 'Sit deleted', 10, 9, 1, true); --sitting sector
INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id, deleted) VALUES ('sitting', 6, 'Sit update', 10, 9, 1, false); --sitting sector

INSERT INTO sectors (sector_type, id , name, capacity, hall_id, deleted) VALUES ('standing', 7, 'Stand to be deleted', 1500, 3, false); --standing sector
INSERT INTO sectors (sector_type, id , name, capacity, hall_id, deleted) VALUES ('standing', 8, 'Stand to be deleted2', 1600, 3, false); --standing sector
INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id, deleted) VALUES ('sitting', 9, 'Sit to be deleted', 10, 9, 3, false); --sitting sector
INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id, deleted) VALUES ('sitting', 10, 'Sit to be deleted2', 10, 9, 3, false); --sitting sector


INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (1, "Dubioza kolektiv, sars, zurka ajoj", "2020-03-03", "2020-03-03", 0, 3, 5, "UNIPARTY", 1, 1, false);
INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (2, "Folk fest", "2020-05-03", "2020-05-03", 0, 3, 5, "FolkFest", 1, 1, false);
	
insert into event_image_paths (event_id, image_paths) values(1, "e1picture1");
insert into event_video_paths (event_id, video_paths) values(1, "e1video1");
insert into event_image_paths (event_id, image_paths) values(2, "e1picture1");
insert into event_video_paths (event_id, video_paths) values(2, "e1video1");

insert into event_days ( id, day_date, description, name, status, event_id , deleted) values(1,"2020-03-03", "Dubioza 21:00, Sars 23:00", "Uniparty Day 1.", 0,1, false);
insert into event_days ( id, day_date, description, name, status, event_id , deleted) values(2,"2020-05-03", "Medjunarodni folk festival", "FolkFest Day 1.", 0,2, false);

insert into event_sectors (id, price, event_id, sector_id, deleted) values (1, 500, 1, 1, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (2, 700, 1, 4, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (3, 400, 2, 1, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (4, 250, 2, 4, false);

insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (1, false, "2019-11-05", 1, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (2, false, "2019-11-05", 1, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (3, false, "2019-11-05", 4, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (4, false, "2019-11-05", 4, false);

--Tickets for event id=1L
insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (1, true,1, 1, 1, 2, 1);
insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (2, true,1, 2, 1, 2, 2);
	
--Tickets for event id = 2L
insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (3, true,1, 1, 2, 4, 3);
insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (4, true,1, 2, 2, 4, 4);
insert into tickets (id, has_seat, event_day_id, sector_id, reservation_id )
	values (5, false, 2, 3, 4);

INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (3, "Event", "2020-05-08", "2020-05-08", 0, 3, 5, "Event", 1, 1, false);	

INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (4, "Event deleted", "2020-05-07", "2020-05-07", 0, 3, 5, "Event deleted", 1, 1, true);
	
INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (5, "Event update", "2020-05-09", "2020-05-09", 0, 3, 5, "Event update", 1, 1, false);
	
INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (6, "Event to be deleted", "2020-05-10", "2020-05-10", 0, 3, 5, "Event to be deleted", 3, 4, false);
	
INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (7, "Event to be deleted2", "2020-05-09", "2020-05-09", 0, 3, 5, "Event to be deleted2", 3, 4, false);
	
insert into event_days (id, day_date,name, description,  status, event_id , deleted) values(3,"2020-06-03", "EventDay", "Status1", 0,2, false);
insert into event_days (id, day_date,name, description,  status, event_id , deleted) values(4,"2020-07-03", "EventDay deleted", "Status2", 0,2, true);
insert into event_days (id, day_date, name, description, status, event_id , deleted) values(5,"2020-08-03", "EventDay update", "Status3", 0,3, false);
insert into event_days (id, day_date,  name, description,status, event_id , deleted) values(6,"2020-09-03", "EventDay to be deleted", "Status4", 0,3, false);
insert into event_days (id, day_date, name,description,  status, event_id , deleted) values(7,"2020-10-03", "EventDay to be deleted2", "Status5", 0,3, false);

insert into event_sectors (id, price, event_id, sector_id, deleted) values (5, 300, 2, 2, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (6, 350, 2, 2, true);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (7, 400, 2, 2, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (8, 420, 2, 2, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (9, 270, 2, 2, false);

insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (5, false, "2019-11-05", 4, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (6, false, "2019-11-06", 4, true);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (7, false, "2019-11-07", 4, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (8, false, "2019-11-08", 4, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (9, false, "2019-11-09", 4, false);