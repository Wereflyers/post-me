create table if not exists users_auth (
                       id                    BIGINT generated by default as identity not null,
                       username              varchar(30) not null unique,
                       password              varchar(80) not null,
                       email                 varchar(50) unique,
                       primary key (id)
);