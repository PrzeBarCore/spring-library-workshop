create table books (
    id int unsigned primary key auto_increment,
    title varchar(50) not null,
    publication_date int unsigned not null,
    publisher_id int not null,
    section_id int not null,
    foreign key (publisher_id) references publishers(id),
    foreign key (section_id) references sections(id)
);
