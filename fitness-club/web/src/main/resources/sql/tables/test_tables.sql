create table if not exists fitness_user(
	id SERIAL not null,
    role varchar(7),
    email varchar(255),
    password varchar(255),
    first_name varchar(255),
    second_name varchar(255),
    discount smallint default 0,
    primary key(id)
);

create table if not exists gym_membership(
	id SERIAL not null,
    months_amount smallint ,
    price decimal(10,2),
    primary key(id)
);

create table if not exists exercise(
	id SERIAL not null,
    name varchar(255),
    primary key(id)
);

create table if not exists client_order(
	id SERIAL not null,
    client_id int,
    begin_date TIMESTAMP,
    end_date TIMESTAMP,
    price decimal(10, 2),
    trainer_id int,
    feedback text,
    nutrition_type varchar(14),
    primary key(id),
    foreign key(trainer_id) references fitness_user(id),
    foreign key(client_id) references fitness_user(id)
);

create table if not exists assignment(
	id SERIAL not null,
    order_id int,
    exercise_id int,
    amount_of_sets smallint,
    amount_of_reps smallint,
    workout_date date,
    status varchar(8) default 'NEW',
    primary key(id),
    foreign key(order_id) references client_order(id),
    foreign key(exercise_id) references exercise(id)
);