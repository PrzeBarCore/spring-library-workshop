package com.github.PrzeBarCore.springlibraryworkshop.dao.adapter;

import com.github.PrzeBarCore.springlibraryworkshop.dao.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SQLAuthorRepository extends AuthorRepository, JpaRepository<Author,Integer> {

    @Override
    @Query("Select distinct a from Author a join fetch a.books")
    List<Author> findAll();


}
