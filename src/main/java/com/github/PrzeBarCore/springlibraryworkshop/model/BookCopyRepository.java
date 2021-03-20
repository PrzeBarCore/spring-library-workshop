package com.github.PrzeBarCore.springlibraryworkshop.model;

import java.util.Optional;

public interface BookCopyRepository {

    BookCopy save(BookCopy source);

    Optional<BookCopy> findById(int id);
}
