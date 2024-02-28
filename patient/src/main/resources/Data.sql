/* Setting up P9 DB */
create database p9_patient;
use p9_patient;

create table patients(
PID bigint PRIMARY KEY AUTO_INCREMENT,
FIRSTNAME varchar(255) NOT NULL,
LASTNAME varchar(255) NOT NULL,
BIRTHDATE varchar(255) NOT NULL,
GENRE varchar(255) NOT NULL,
ADDRESS varchar(255),
PHONE varchar(255)
);

commit;
create database p9_report;
use p9_report;

create table reports(
RID bigint PRIMARY KEY AUTO_INCREMENT,
PID bigint NOT NULL,
DATE DATETIME NOT NULL,
DESCRIPTION varchar(255) NOT NULL,
FOREIGN KEY (PID) REFERENCES patients(PID)
);

create table criteria(
CID bigint PRIMARY KEY AUTO_INCREMENT,
NAME varchar(255) NOT NULL
);

insert into criterion(NAME) values('Hémoglobine A1C');
insert into criterion(NAME) values('Microalbumine');
insert into criterion(NAME) values('Taille');
insert into criterion(NAME) values('Poids');
insert into criterion(NAME) values('Fumeur, Fumeuse');
insert into criterion(NAME) values('Anormal');
insert into criterion(NAME) values('Cholestérol');
insert into criterion(NAME) values('Vertige');
insert into criterion(NAME) values('Rechute');
insert into criterion(NAME) values('Réaction');
insert into criterion(NAME) values('Anticorps');

commit;
create database p9_auth;
use p9_auth;

create table users(
UID bigint PRIMARY KEY AUTO_INCREMENT,
EMAIL varchar(255) NOT NULL,
FIRSTNAME varchar(255) NOT NULL,
LASTNAME varchar(255) NOT NULL,
PASSWORD varchar(255) NOT NULL,
ROLE varchar(255) NOT NULL
);
commit;