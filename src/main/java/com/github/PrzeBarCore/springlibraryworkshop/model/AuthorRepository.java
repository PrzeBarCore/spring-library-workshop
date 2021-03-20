package com.github.PrzeBarCore.springlibraryworkshop.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(Integer id);

    boolean existsAuthorById(Integer id);

    boolean existsAuthorByNameAndLastName(String name, String lastName);

    Page<Author> findAll(Pageable page);

    Author save(Author source);

    List<Author> findAll();

}
