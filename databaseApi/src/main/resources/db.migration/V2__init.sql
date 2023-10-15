drop database "dictionaryAPI";
create database "dictionaryAPI";
create TABLE if not exists public.users
(
    id                bigserial primary key,
    tg_name           VARCHAR(256) not null,
    username          VARCHAR(256) not null,
    registration_date timestamp(6) not null,
    chat_id           bigint not null
);

create TABLE if not exists public.settings
(
    id            bigserial
        constraint settings_pkey
            primary key,
    user_id       bigint not null
        constraint settings_userid_key
            unique
        constraint userid
            references users,
    interfacelang varchar(256) not null,
    native        varchar(256),
    notifications boolean      not null
);

alter table users
    owner to postgres;

alter table settings
    owner to postgres;