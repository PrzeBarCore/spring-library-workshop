package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookAuthorWriteModel;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    AuthorService(final AuthorRepository repository) {
        this.repository = repository;
    }


    public Author createAuthor(BookAuthorWriteModel source, Book book) {
        Author author= source.toAuthor(book);
        Author result = repository.save(author);
        return result;
    }


    public Author readAuthor(Integer id){
        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Author with given ID doesn't exist!"));
    }

    public void deleteAuthor(Integer id) {
        if(!repository.existsAuthorById(id)){
            throw new IllegalArgumentException("Author with given ID doesn't exist!");
        }
        repository.deleteAuthorById(id);
    }
}
