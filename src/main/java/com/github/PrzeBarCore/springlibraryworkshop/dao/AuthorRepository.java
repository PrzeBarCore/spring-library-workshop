package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(Integer id);

    boolean existsAuthorById(Integer id);

    Author save(Author source);

    Page<Author> findAll(Pageable pageable);

    void deleteAuthorById(Integer id);
}
