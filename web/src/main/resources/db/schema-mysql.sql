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


