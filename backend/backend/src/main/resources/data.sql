-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Ovo su sve imena kolona u tabelama db(Column anotacija u klasama), a ne imena polja u klasama

INSERT INTO authorities (id, role) VALUES (1, 'ROLE_REGISTERED_USER');
INSERT INTO authorities (id, role) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authorities (id, role) VALUES (3, 'ROLE_SYS_ADMIN');


-- password is 'user' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'user', 1, 'user', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Bob', 'Bobic', 'bob@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false);
-- password is 'admin' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'admin', 2, 'admin', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Tom', 'Tomic', 'tom@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false);
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'sys', 3, 'sys', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Jovan', 'Jovanovic', 'tirmann25@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false);


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1); -- registered user has ROLE_REGISTERED_USER
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2); -- adminstrator has ROLE_ADMIN
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 3); -- system adminstrator has ROLE_SYS_ADMIN

INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (1, "Street", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);

INSERT INTO locations (id, name, description, address_id, deleted) values (1, "SPENS NS", "Biggest location for sports in Vojvodina", 1, false);

INSERT INTO halls (id, name, number_of_sectors, location_id, deleted) values (1, "Main Hall", 2, 1, false);

INSERT INTO sectors (sector_type, id , name , capacity, hall_id, deleted) VALUES ('standing', 1, 'S1_1', 500, 1, false);	--standing sector
INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id, deleted) VALUES ('sitting', 2, 'S2_2', 10, 10, 1, false); --sitting sector

INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, deleted) 
	values (1, "Dubioza kolektiv, sars, zurka ajoj", "2020-03-03", "2020-03-03", 0, 3, 5, "UNIPARTY", 1, false);
	
insert into event_image_paths (event_id, image_paths) values(1, "e1picture1");
insert into event_video_paths (event_id, video_paths) values(1, "e1video1");

insert into event_days ( id, day_date, description, name, status, event_id , deleted) values(1,"2020-03-03", "Dubioza 21:00, Sars 23:00", "Uniparty Day 1.", 0,1, false);

insert into event_sectors (id, price, event_id, sector_id, deleted) values (1, 500, 1, 1, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (2, 700, 1, 2, false);

insert into reservations (id, purchased, reservation_date, buyer_id , deleted) values (1, false, "2019-11-05", 1, false);
insert into reservations (id, purchased, reservation_date, buyer_id , deleted) values (2, false, "2019-11-05", 1, true);

insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id , deleted)
	values (1, true ,1, 1, 1, 2, 1, false);

insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id , deleted)
	values (2, true ,1, 2, 1, 2, 2, true);


        
        
