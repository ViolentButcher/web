CREATE TABLE cluster(id integer auto_increment primary key ,name varchar(20) unique not null ,attribute varchar(100),configuration varchar(30),create_time datetime not null ,modify_time datetime not null,state bool)default charset = utf8;
create table dataset_article
(
    id int auto_increment primary key,
    title varchar(30) unique not null ,
    author varchar(20) not null,
    publish_time varchar(20) not null,
    journal varchar(20) not null,
    word varchar(20) not null,
    summary varchar(1000) not null
) default charset = utf8;