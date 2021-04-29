package com.github.PrzeBarCore.springlibraryworkshop.dao;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Page<Book> findAll(Pageable page);

    Optional<Book> findById(Integer id);

    void deleteBookById(Integer id);

    Book save(Book source);



}
