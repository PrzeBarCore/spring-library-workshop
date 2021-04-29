package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuthorRepository {

    Page<Author> findAll(Pageable pageable);

    Optional<Author> findById(Integer id);

    void deleteAuthorById(Integer id);

    Author save(Author source);
}
