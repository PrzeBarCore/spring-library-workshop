create table book_copies(
    id int unsigned primary key auto_increment,
    state int not null default 0,
    reserved_until datetime null,
    borrowed_until datetime null,
    book_edition_id int not null,
    foreign key (book_edition_id) references book_editions(id)
);

insert into book_copies (book_edition_id, state)
select book_editions.id, book_editions.state from book_editions;

alter table book_editions drop state;