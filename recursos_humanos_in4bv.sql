drop database if exists recursos_humanos_in4bv;
create database recursos_humanos_in4bv;
use recursos_humanos_in4bv;

create table roles(
role_id int auto_increment primary key not null,
role_name ENUM('Contractor', 'Applicant')
);

create table users(
user_id varchar(60) primary key not null,
first_name varchar(60) not null, 
last_name varchar(60) not null,
email varchar(120) unique not null, 
password_hash varchar(120) unique not null, 
role_id int auto_increment,
phone_number varchar(40),
age int not null,
foreign key (role_id)
references roles(role_id)
);

create table addresses(
address_id varchar(120) primary key not null,
country int not null,
state varchar(120) not null,
city varchar(120) not null,
zone varchar(120),
house varchar(50),
street varchar(50),
avenue varchar(50)

);

