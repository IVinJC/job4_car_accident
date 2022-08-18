CREATE TABLE accident
(
    id   serial primary key,
    name varchar(2000),
    text text,
    address varchar(2000),
    rule_id int not null references accident(id),
    type_id int not null references type(id)
);

CREATE TABLE rule
(
    id   serial primary key,
    name varchar(2000)
);

CREATE TABLE type
(
    id   serial primary key,
    name varchar(2000)
);