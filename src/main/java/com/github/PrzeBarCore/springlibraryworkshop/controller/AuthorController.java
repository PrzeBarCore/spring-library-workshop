package com.github.PrzeBarCore.springlibraryworkshop.controller;


import com.github.PrzeBarCore.springlibraryworkshop.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/authors")
class AuthorController {
    private final AuthorService service;
    private static final Logger logger= LoggerFactory.getLogger(AuthorController.class);

    AuthorController(final AuthorService service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteAuthor(Integer id){
        service.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
