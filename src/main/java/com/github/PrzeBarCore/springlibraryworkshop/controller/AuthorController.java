package com.github.PrzeBarCore.springlibraryworkshop.controller;


import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookAuthorsRespAuthorDTO;
import com.github.PrzeBarCore.springlibraryworkshop.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
class AuthorController {
    private final AuthorService service;
    private static final Logger logger= LoggerFactory.getLogger(AuthorController.class);

    AuthorController(final AuthorService service) {
        this.service = service;
    }

    @GetMapping
    String readAllAuthors(){
        return "authors";
    }

    @GetMapping("/{id}")
    String readAuthor(@PathVariable int id, Model model){
        model.addAttribute("author",service.getAuthorReadModelById(id));
        return "authorDisplay";
    }

    @ModelAttribute
    void getAllAuthors(@PageableDefault(size=10) Pageable pageable, Model model){
        Page<BookAuthorsRespAuthorDTO> result=service.readAllAuthors(pageable);

        int pageNumber= pageable.getPageNumber();
        int totalPages= result.getTotalPages();

        if(pageNumber>=totalPages){
            model.addAttribute("pageNumber", 0);
        } else {
            model.addAttribute("pageNumber", pageNumber);
        }

        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("authors",result);
        model.addAttribute("totalPages", totalPages);
    }
}
