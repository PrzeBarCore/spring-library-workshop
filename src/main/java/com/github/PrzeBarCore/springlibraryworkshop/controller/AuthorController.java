package com.github.PrzeBarCore.springlibraryworkshop.controller;


import com.github.PrzeBarCore.springlibraryworkshop.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
@Controller
@RequestMapping("/authors")
class AuthorController {
    private AuthorService service;
    private static final Logger logger= LoggerFactory.getLogger(AuthorController.class);

    AuthorController(final AuthorService service) {
        this.service = service;
    }

//    ///Do przemyślenia
//    @PostMapping
//    ResponseEntity<AuthorReadModel> createAuthor(@RequestBody @Valid AuthorWriteModel toCreate){
//        logger.warn("Author created!");
//        return ResponseEntity.ok(service.createAuthor(toCreate));
//   }
//

    ////Wyjątek
//    @GetMapping("/{id}")
//    ResponseEntity<AuthorReadModel> readAuthor(@PathVariable int id) throws IOException {
//        logger.warn("Exposing author wih id: "+id);
//
//        return ResponseEntity.ok(service.readAuthor(id));
//    }

    @GetMapping
    String readAllAuthors(@RequestParam(value = "pageNumber") Optional<Integer> pageNumber, @RequestParam(value = "pageSize")Optional<Integer> pageSize ) {
        logger.warn("Exposing all the authors!");
        return "authors";
    }

//    @ModelAttribute("authors")
//    List<BookAuthorModel> getAuthors(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
//        return service.findAll(pageNumber,pageSize);
//    }
}
