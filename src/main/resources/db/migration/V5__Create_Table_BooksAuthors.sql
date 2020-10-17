create table books_authors (
    book_id int not null,
    author_id int not null,
    foreign key (book_id) references books(id),
    foreign key (author_id) references authors(id)
);
