package com.github.PrzeBarCore.springlibraryworkshop.controller;



import com.github.PrzeBarCore.springlibraryworkshop.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/sections")
class SectionController {
    private SectionService service;
    private static final Logger logger= LoggerFactory.getLogger(AuthorController.class);

    SectionController(final SectionService service) {
        this.service = service;
    }

    @GetMapping
    String readAllSections(@RequestParam(value = "pageNumber") Optional<Integer> pageNumber, @RequestParam(value = "pageSize")Optional<Integer> pageSize ) {
        logger.warn("Exposing all the sections!");
        return "sections";
    }
//
//    @ModelAttribute("sections")
//    List<BookSectionModel> getSections(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
//        return service.findAll(pageNumber,pageSize);
//    }


}
