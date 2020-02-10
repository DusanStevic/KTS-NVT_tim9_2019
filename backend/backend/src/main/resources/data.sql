-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Ovo su sve imena kolona u tabelama db(Column anotacija u klasama), a ne imena polja u klasama

INSERT INTO authorities (id, role) VALUES (1, 'ROLE_REGISTERED_USER');
INSERT INTO authorities (id, role) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authorities (id, role) VALUES (3, 'ROLE_SYS_ADMIN');


-- password is 'user' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time, image_url) VALUES ( 'user', 1, 'user', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Bob', 'Bobic', 'bob@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false, 'https://res.cloudinary.com/djxkexzcr/image/upload/v1574108111/zbvvptxlxzzhzomjvp2s.jpg');
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time, image_url) VALUES ( 'user', 4, 'user2', '$2a$10$NBqWWykrf7S2o2V2ja5E7OaDCbjAqcghBIk9dOl1Bj3UIsooKJMkK','Arpad', 'Varga Somodji', 'tirmann25+10@gmail.com', true, '2019-04-18 20:58:00', '0658845455', false, 'https://res.cloudinary.com/djxkexzcr/image/upload/v1574108111/zbvvptxlxzzhzomjvp2s.jpg');
-- password is 'admin' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time, image_url) VALUES ( 'admin', 2, 'admin', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Tom', 'Tomic', 'tom@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false, 'https://res.cloudinary.com/djxkexzcr/image/upload/v1574108111/zbvvptxlxzzhzomjvp2s.jpg');
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time, image_url) VALUES ( 'sys', 3, 'sys', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Jovan', 'Jovanovic', 'tirmann25@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', false, 'https://res.cloudinary.com/djxkexzcr/image/upload/v1574108111/zbvvptxlxzzhzomjvp2s.jpg');


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1); -- registered user has ROLE_REGISTERED_USER
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 1); -- registered user has ROLE_REGISTERED_USER
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2); -- adminstrator has ROLE_ADMIN
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 3); -- system adminstrator has ROLE_SYS_ADMIN

INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude, deleted) VALUES (1, "Street", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549, false);

INSERT INTO locations (id, name, description, deleted, address, latitude, longitude) values (1, "SPENS NS", "Biggest location for sports in Vojvodina", "1970-01-01 01:00:00", "Street 2 Novi Sad Serbia", 45.267136, 19.833549);

INSERT INTO halls (id, name, location_id, deleted) values (1, "Main Hall", 1, false);

INSERT INTO sectors (sector_type, id , name , capacity, hall_id, deleted) VALUES ('standing', 1, 'S1_1', 500, 1, false);	--standing sector
INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id, deleted) VALUES ('sitting', 2, 'S2_2', 10, 10, 1, false); --sitting sector

INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, hall_id, deleted) 
	values (1, "Dubioza kolektiv, sars, zurka ajoj", "2020-03-03", "2020-03-03", 0, 3, 5, "UNIPARTY", 1, 1, false);
INSERT INTO events (id, description, start_date, end_date, event_type, num_days, max_tickets, name, location_id, deleted) 
	values (2, "Folk fest", "2020-05-03", "2020-05-03", 0, 3, 5, "FolkFest", 1, false);
	
insert into event_image_paths (event_id, image_paths) values(1, "https://res.cloudinary.com/djxkexzcr/image/upload/v1574108286/lf4ddnka9rqe62creizz.jpg");
insert into event_image_paths (event_id, image_paths) values(1, "https://res.cloudinary.com/djxkexzcr/image/upload/v1573920002/sample.jpg");
insert into event_video_paths (event_id, video_paths) values(1, "https://res.cloudinary.com/djxkexzcr/video/upload/v1581221186/All_over_in_10_seconds_ppphnm.mp4");
insert into event_video_paths (event_id, video_paths) values(1, "https://res.cloudinary.com/djxkexzcr/video/upload/v1581223053/video_vuvorc.mp4");
insert into event_image_paths (event_id, image_paths) values(2, "https://res.cloudinary.com/djxkexzcr/image/upload/v1574108286/lf4ddnka9rqe62creizz.jpg");
insert into event_video_paths (event_id, video_paths) values(2, "https://res.cloudinary.com/djxkexzcr/video/upload/v1581221186/All_over_in_10_seconds_ppphnm.mp4");

insert into event_days ( id, day_date, description, name, status, event_id , deleted) values(1,"2020-03-03", "Dubioza 21:00, Sars 23:00", "Uniparty Day 1.", 0,1, false);
insert into event_days ( id, day_date, description, name, status, event_id , deleted) values(2,"2020-05-03", "Medjunarodni folk festival", "FolkFest Day 1.", 0,2, false);

insert into event_sectors (id, price, event_id, sector_id, deleted) values (1, 500, 1, 1, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (2, 700, 1, 2, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (3, 400, 2, 1, false);
insert into event_sectors (id, price, event_id, sector_id, deleted) values (4, 250, 2, 2, false);

insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (1, false, "2019-11-05", 1, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (2, false, "2019-11-05", 1, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (3, false, "2019-11-05", 4, false);
insert into reservations (id, purchased, reservation_date, buyer_id, canceled) values (4, false, "2019-11-05", 4, false);

insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (1, true,1, 1, 1, 2, 1);
insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (2, true,1, 2, 1, 2, 2);
		
insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (3, true,1, 1, 2, 4, 3);
insert into tickets (id, has_seat, num_col, num_row, event_day_id, sector_id, reservation_id )
	values (4, true,1, 2, 2, 4, 4);
insert into tickets (id, has_seat, event_day_id, sector_id, reservation_id )
	values (5, false, 2, 3, 4);
        
        
