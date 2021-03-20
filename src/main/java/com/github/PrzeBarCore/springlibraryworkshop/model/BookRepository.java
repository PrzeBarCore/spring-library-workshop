package com.github.PrzeBarCore.springlibraryworkshop.model;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(Integer id);

    List<Book> findAll();

    Page<Book> findAll(Pageable page);

    Book save(Book source);

    List<Book> findBySection_Id(Integer sectionId);

    boolean existsBookById(Integer id);

    void deleteBookById(Integer id);

}
