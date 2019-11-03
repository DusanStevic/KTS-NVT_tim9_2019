-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Ovo su sve imena kolona u tabelama db(Column anotacija u klasama), a ne imena polja u klasama

INSERT INTO authorities (id, role) VALUES (1, 'ROLE_REGISTERED_USER');
INSERT INTO authorities (id, role) VALUES (2, 'ROLE_ADMINSTRATOR');


        
--INSERT INTO USERS (id, username, password, first_name, last_name, email, enabled, last_password_reset_date) VALUES (1, 'user', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'user@example.com', true, '2017-10-01 21:58:58.508-07');
--INSERT INTO USERS (id, username, password, first_name, last_name, email, enabled, last_password_reset_date) VALUES (2, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nikola', 'Nikolic', 'admin@example.com', true, '2017-10-01 18:57:58.508-07');
--insert into users (email, enabled, first_time, last_password_reset_date, name, password, surname, username, airline_id, dtype, id) values ('bobic@gmail.com', 1, 1, '2019-04-28 21:00:00', 'Bob', '$2a$10$c7jv8zcmZoNkjkLZAdTE5OmvmPddtMzMn6GLYt62lymTYu5vdpfpG', 'Bobic', 'avion', 1, 'AirlineAdmin', 2);



INSERT INTO users (dtype, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'RegisteredUser', 1, 'user', '$2a$10$YdRXQKq0qvhfrXU3.iOLXOMd/G0mrVWpe56EmN/CGD7/MDBadA/iS','Bob', 'Bobic', 'bob@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', true);
INSERT INTO users (dtype, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'Administrator', 2, 'admin', '$2a$10$mj6jiG5FLHp4s2Nvf84mbeeYPjjFmeDDtk7Uzua8sBBWzuXtNoPqW','Tom', 'Tomic', 'tom@gmail.com', true, '2019-04-18 20:58:00', '00381643332211', true);


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1); -- registered user has ROLE_REGISTERED_USER
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2); -- adminstrator has ROLE_ADMINSTRATOR


        
        
