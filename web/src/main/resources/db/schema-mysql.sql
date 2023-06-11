drop table if exists food_ordering.food_category;

create table food_ordering.food_category
(
    cat_id    int auto_increment comment 'category id'
        primary key,
    cat_name  varchar(12) not null comment 'category name',
    cat_desp  varchar(32) null comment 'category description',
    cat_label varchar(12) not null comment 'category label'
)
    comment 'food category';

drop table if exists food_ordering.food;

create table food_ordering.food
(
    food_id     int auto_increment comment 'food id'
        primary key,
    food_name   varchar(24)  not null comment 'food name',
    food_desp   varchar(32)  null comment 'food description',
    food_price  float        not null comment 'food price',
    food_img    varchar(255) not null comment 'food img path',
    create_time datetime     null comment 'food create time'
)
    comment 'food information';

drop table if exists food_ordering.food_info;

create table food_ordering.food_info
(
    cat_id  int not null,
    food_id int not null
);

drop table if exists food_ordering.user;

create table food_ordering.user
(
    user_id  int auto_increment comment 'user id'
        primary key,
    username varchar(32)  not null comment 'user name',
    password varchar(128) not null comment 'user password',
    salt     varchar(64)  null
)
    comment 'user';

drop table if exists food_ordering.user_info;

create table food_ordering.user_info
(
    info_id     int auto_increment comment 'info id'
        primary key,
    user_id     int          not null comment 'use''s id',
    email       varchar(128) null comment 'user email',
    phone_number varchar(20)  null comment 'user''s phone number'
)
    comment 'user other information';

drop table if exists food_ordering.user_authz;

create table food_ordering.user_authz
(
    authz_id int auto_increment
        primary key,
    user_id  int         null comment 'user''s id',
    role     varchar(64) null comment 'user''s role name'
)
    comment 'user role and resource and more';

drop table if exists food_ordering.auth_token;

create table food_ordering.auth_token
(
    token_id   int auto_increment comment 'token id'
        primary key,
    token      varchar(256) null,
    token_type varchar(32) null,
    revoked    int(8)      null,
    expired    int(8)      null,
    user_id    int         null
)
    comment 'authtication token';



