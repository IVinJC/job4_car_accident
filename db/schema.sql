CREATE TABLE accident
(
    id   serial primary key,
    name varchar(2000),
    text text,
    address varchar(2000),
    type_id int not null references type(id)
);

CREATE TABLE rule
(
    id   serial primary key,
    name varchar(2000) unique not null
);

CREATE TABLE type
(
    id   serial primary key,
    name varchar(2000) unique not null
);

CREATE TABLE accident_rule
(
    id serial primary key,
    accident_id int references accident(id),
    rule_id int references rule(id)
);