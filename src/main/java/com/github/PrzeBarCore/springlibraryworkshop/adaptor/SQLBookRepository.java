package com.github.PrzeBarCore.springlibraryworkshop.adaptor;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SQLBookRepository extends BookRepository, JpaRepository<Book,Integer> {

    @Override
    @Query("Select distinct b from Book b join fetch b.authors join fetch b.section join fetch b.publisher")
    List<Book> findAll();

//    @Override
//    @Query("Select distinct b from Book b join fetch b.authors join fetch b.section join fetch b.publisher")
//    Page<Book> findAll(Pageable page);


}
