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

INSERT INTO adresses (id, street_name, street_number, city, country, latitude, longitude) VALUES (1, "Street", 2, "Novi Sad" , "Serbia", 45.267136, 19.833549);

INSERT INTO locations (id, name, description, address_id) values (1, "SPENS NS", "Biggest location for sports in Vojvodina", 1);

INSERT INTO halls (id, name, number_of_sectors, location_id) values (1, "Main Hall", 2, 1);

INSERT INTO sectors (sector_type, id , name , capacity, hall_id) VALUES ('standing', 1, 'S1_1', 500, 1);	--standing sector
INSERT INTO sectors (sector_type, id , name, num_cols , num_rows, hall_id) VALUES ('sitting', 2, 'S2_2', 10, 10, 1) --sitting sector
        
        
