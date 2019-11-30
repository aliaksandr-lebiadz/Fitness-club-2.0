create database if not exists fitness;
use fitness;

create table if not exists fitness_user(
	id int not null auto_increment,
    role enum('client', 'trainer', 'admin') not null,
    email varchar(254) not null,
    password varchar(30) not null,
    first_name varchar(30),
    second_name varchar(30),
    discount tinyint not null default 0,
    primary key(id)
);

create table if not exists gym_membership(
	id int not null auto_increment,
    months_amount tinyint not null,
    price decimal(10,2) not null,
    primary key(id)
);

create table if not exists exercise(
	id int not null auto_increment,
    name varchar(40) not null,
    primary key(id)
);

create table if not exists client_order(
	id int not null auto_increment,
    client_id int not null,
    begin_date datetime not null,
    end_date datetime not null,
    price decimal(10, 2) not null,
    trainer_id int not null,
    feedback text,
    nutrition_type enum('low calorie', 'medium calorie', 'high calorie'),
    primary key(id),
    foreign key(trainer_id) references fitness_user(id),
    foreign key(client_id) references fitness_user(id)
);

create table if not exists assignment(
	id int not null auto_increment,
    order_id int not null,
    exercise_id int not null,
    amount_of_sets tinyint not null,
    amount_of_reps tinyint not null,
    workout_date date not null,
    status enum('new', 'changed', 'accepted', 'canceled') not null default 'new',
    primary key(id),
    foreign key(order_id) references client_order(id),
    foreign key(exercise_id) references exercise(id)
);