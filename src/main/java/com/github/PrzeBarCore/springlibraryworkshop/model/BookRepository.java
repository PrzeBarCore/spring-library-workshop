package com.github.PrzeBarCore.springlibraryworkshop.model;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(Integer id);

    List<Book> findAll();

    List<Book> findBySection_Id(Integer sectionId);

}
