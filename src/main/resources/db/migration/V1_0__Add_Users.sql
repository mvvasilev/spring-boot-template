create schema if not exists "app";

create table if not exists "app"."User" (
    "id" numeric auto_increment,
    "username" varchar(50),
    "email" varchar(255),
    "passwordHash" varchar(2048),
    constraint "PK_User" primary key ("id"),
    constraint "UQ_User_email" unique ("email"),
    constraint "UQ_User_username" unique ("username")
);