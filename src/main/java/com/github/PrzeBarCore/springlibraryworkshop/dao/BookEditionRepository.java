package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface BookEditionRepository {

    BookEdition save(BookEdition source);

    Optional<BookEdition> findById(int id);

    List<Optional<BookEdition>> readByBookAndPublicationDateAndPublisher(Book book, Year publicationDate, Publisher publisher);

    void deleteById(int id);
}
