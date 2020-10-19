package com.github.PrzeBarCore.springlibraryworkshop.model;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(Integer id);

    List<Author> findAll();

}
