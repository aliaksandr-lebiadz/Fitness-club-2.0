create table if not exists fitness_user(
	id SERIAL not null,
    role varchar(7) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    first_name varchar(255),
    second_name varchar(255),
    discount smallint not null default 0,
    primary key(id)
);

create table if not exists gym_membership(
	id SERIAL not null,
    months_amount smallint not null,
    price decimal(10,2) not null,
    primary key(id)
);

create table if not exists exercise(
	id SERIAL not null,
    name varchar(255) not null,
    primary key(id)
);

create table if not exists client_order(
	id SERIAL not null,
    client_id int not null,
    begin_date TIMESTAMP not null,
    end_date TIMESTAMP not null,
    price decimal(10, 2) not null,
    trainer_id int not null,
    feedback text,
    nutrition_type varchar(14),
    primary key(id),
    foreign key(trainer_id) references fitness_user(id),
    foreign key(client_id) references fitness_user(id)
);

create table if not exists assignment(
	id SERIAL not null,
    order_id int not null,
    exercise_id int not null,
    amount_of_sets smallint not null,
    amount_of_reps smallint not null,
    workout_date date not null,
    status varchar(8) not null default 'NEW',
    primary key(id),
    foreign key(order_id) references client_order(id),
    foreign key(exercise_id) references exercise(id)
);