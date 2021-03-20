package com.github.PrzeBarCore.springlibraryworkshop.model;

import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(Integer id);

    boolean existsAuthorById(Integer id);

    Author save(Author source);

    void deleteAuthorById(Integer id);

}
