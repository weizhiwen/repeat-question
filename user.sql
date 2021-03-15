# user1 和 user2 的区别是有无唯一约束
create table user1
(
    id       int auto_increment
        primary key,
    tel      varchar(16) null,
    password varchar(32) null
);

create table user2
(
    id       int auto_increment
        primary key,
    tel      varchar(16) null unique,
    password varchar(32) null
);


