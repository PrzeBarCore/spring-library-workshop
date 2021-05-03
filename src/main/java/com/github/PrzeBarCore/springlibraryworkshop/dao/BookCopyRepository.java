package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

import java.util.Optional;

public interface BookCopyRepository {
    Optional<BookCopy> findById(int id);

    void deleteById(int id);

    BookCopy save(BookCopy result);
}
