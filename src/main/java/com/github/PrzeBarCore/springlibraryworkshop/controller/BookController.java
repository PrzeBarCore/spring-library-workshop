package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReadModel;
import com.github.PrzeBarCore.springlibraryworkshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private BookRepository repository;
    private BookService service;

    BookController(final BookRepository repository, final BookService service) {
        this.repository = repository;
        this.service= service;
    }


    @GetMapping
    String readAllBooks(@RequestParam(value = "pageNumber") Optional<Integer> pageNumber, @RequestParam(value = "pageSize")Optional<Integer> pageSize ) {
        logger.warn("Exposing all the books!");
        return "books";
    }

    @ModelAttribute("books")
    List<BookReadModel> getBooks(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        return service.findAll(pageNumber,pageSize);
    }

}
