drop table if exists books_authors;
drop table if exists book_copies;
drop table if exists books CASCADE CONSTRAINTS;
drop table if exists authors CASCADE CONSTRAINTS;
drop table if exists sections;
drop table if exists publishers;

create table authors (
    id int unsigned primary key auto_increment,
    name varchar(40) not null,
    last_name varchar(40)
);
