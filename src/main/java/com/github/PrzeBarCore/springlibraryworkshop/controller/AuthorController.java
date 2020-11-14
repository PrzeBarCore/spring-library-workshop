package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.AuthorRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookAuthorReadModel;
import com.github.PrzeBarCore.springlibraryworkshop.service.AuthorService;
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
@RequestMapping("/authors")
class AuthorController {
    private AuthorRepository repository;
    private AuthorService service;
    private static final Logger logger= LoggerFactory.getLogger(AuthorController.class);

    AuthorController(final AuthorService service) {
        this.service = service;
    }

    @GetMapping
    String readAllAuthors(@RequestParam(value = "pageNumber") Optional<Integer> pageNumber, @RequestParam(value = "pageSize")Optional<Integer> pageSize ) {
        logger.warn("Exposing all the authors!");
        return "authors";
    }

    @ModelAttribute("authors")
    List<BookAuthorReadModel> getAuthors(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        return service.findAll(pageNumber,pageSize);
    }
}
