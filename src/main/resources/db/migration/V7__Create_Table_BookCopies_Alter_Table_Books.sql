
create table book_copies(
                         id int unsigned primary key auto_increment,
                         state int not null default 0,
                         publication_date int unsigned,
                         publisher_id int not null,
                         book_id int not null,
                         foreign key (publisher_id) references publishers(id),
                         foreign key (book_id) references books(id));

insert into book_copies (publication_date,publisher_id, book_id)
            select books.publication_date,books.publisher_id, books.id
            from books;

alter table books drop publication_date, publisher_id;

