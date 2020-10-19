package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private BookRepository repository;

    BookController(final BookRepository repository) {
        this.repository = repository;
    }
    //TODO
    @GetMapping("/books")
    ResponseEntity<?> readAllBooks() {
        logger.warn("Exposing all the books!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/book")
    ResponseEntity<?> readBooksBySection_Id() {

        return ResponseEntity.ok(repository.findBySection_Id(1));
    }

}
