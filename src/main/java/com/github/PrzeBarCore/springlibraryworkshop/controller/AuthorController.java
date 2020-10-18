package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthorController {
    private AuthorRepository repository;
    private static final Logger logger= LoggerFactory.getLogger(AuthorController.class);

    AuthorController(final AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/authors")
    ResponseEntity<?> findAllAuthors(){
        logger.warn("Exposing all the authors!");
        return ResponseEntity.ok(repository.findAll());
    }
}
