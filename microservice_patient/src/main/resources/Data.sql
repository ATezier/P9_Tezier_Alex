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