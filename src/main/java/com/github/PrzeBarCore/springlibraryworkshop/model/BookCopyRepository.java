package com.github.PrzeBarCore.springlibraryworkshop.model;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

import java.util.Optional;

public interface BookCopyRepository {

    BookCopy save(BookCopy source);

    Optional<BookCopy> findById(int id);
}
