package com.github.PrzeBarCore.springlibraryworkshop.dao.adapter;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SQLBookRepository extends BookRepository, JpaRepository<Book,Integer> {

    @Override
    @Query("Select distinct b from Book b join fetch b.authors")
    List<Book> findAll();





}
