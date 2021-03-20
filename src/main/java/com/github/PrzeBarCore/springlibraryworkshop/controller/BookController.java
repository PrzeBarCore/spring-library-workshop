package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookReadModel;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookWriteModel;
import com.github.PrzeBarCore.springlibraryworkshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookService service;

    BookController(final BookService service) {
        this.service= service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<BookReadModel> createBook(@Valid @RequestBody BookWriteModel toCreate){

        return ResponseEntity.ok(service.createBook(toCreate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookReadModel> readBook(@PathVariable Integer id){
        return ResponseEntity.ok(service.readBook(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BookReadModel> updateBook(@RequestBody BookWriteModel toUpdate, @PathVariable Integer id){
        return ResponseEntity.ok(service.updateBook(toUpdate,id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteBook(@PathVariable Integer id){
        service.deleteBook(id);
        return ResponseEntity.noContent().build();

    }

}
