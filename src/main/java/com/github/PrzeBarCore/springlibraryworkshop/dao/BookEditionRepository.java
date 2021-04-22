package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import java.util.Optional;

public interface BookEditionRepository {

    BookEdition save(BookEdition source);

    Optional<BookEdition> findById(int id);
}
