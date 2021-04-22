package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookRespBookDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookDTO;
import com.github.PrzeBarCore.springlibraryworkshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
class BookController {
    private final BookService service;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    BookController(final BookService service) {
        this.service= service;
    }

//    @PostMapping
//    @Transactional
//    public ResponseEntity<BookReadModel> createBook(@Valid @RequestBody BookWriteModel toCreate){
//
//        return ResponseEntity.ok(service.createBook(toCreate));
//    }

    @PostMapping
    @Transactional
    String createBook(@ModelAttribute("bookToCreate") BookReqBookDTO toCreate){
        service.createBook(toCreate).getId();
        return "books";
    }

    @PostMapping(params = "addAuthor")
    String addAuthor(@ModelAttribute("bookToCreate") BookReqBookDTO current){
        current.addAuthor();
        return "bookForm";
    }

    @PostMapping(params="removeAuthor")
    String removeAuthor(@ModelAttribute("bookToCreate") BookReqBookDTO current, @RequestParam("removeAuthor") int authorIndexToRemove ){
        current.removeAuthor(authorIndexToRemove);
        return "bookForm";
    }

    @PostMapping(params = "addBookCopy")
    String addBookEdition(@ModelAttribute("bookToCreate") BookReqBookDTO current){
        current.addBookEdition();
        return "bookForm";
    }

    @PostMapping(params="removeBookCopy")
    String removeBookEdition(@ModelAttribute("bookToCreate") BookReqBookDTO current, @RequestParam("removeBookCopy") int copyIndexToRemove ){
        current.removeBookEdition(copyIndexToRemove);
        return "bookForm";
    }

    @GetMapping
    String readAllBooks(){
        return "books";
    }


    @GetMapping("/{id}")
    String readBook(@PathVariable Integer id, Model model){
        BookRespBookDTO book= service.getBookReadModelById(id);
        model.addAttribute("book", book);
        return "bookDisplay";
    }

    @PostMapping("/reserve/{id}")
    String reserveBookCopy(@PathVariable Integer id, @ModelAttribute("book") BookRespBookDTO current) {
        service.reserveBookCopy(id);
        return "bookDisplay";
    }

    @GetMapping("/new")
    String showBookForm(Model model){
        BookReqBookDTO book= new BookReqBookDTO();
        model.addAttribute("bookToCreate",book);
        return "bookForm";
    }

    @GetMapping("/update/{id}")
    String showBookForm(@PathVariable int id, Model model){
        BookRespBookDTO book= service.getBookReadModelById(id);
        model.addAttribute("bookToCreate",book);
        return "bookForm";
    }

    @PutMapping("/{id}")
    @Transactional
    ResponseEntity<BookRespBookDTO> updateBook(@RequestBody BookReqBookDTO toUpdate, @PathVariable Integer id){
        return ResponseEntity.ok(service.updateBook(toUpdate,id));
    }

    @PostMapping("/{id}")
    @Transactional
    String deleteBook(@PathVariable Integer id){
        service.deleteBook(id);
        return "books";

    }

    @ModelAttribute
    void getAllBooks( @PageableDefault(size=10) Pageable pageable, Model model){
        Page<BookRespBookDTO> result=service.readAllBooks(pageable);

        int pageNumber=pageable.getPageNumber();
        int totalPages=result.getTotalPages();

        if(pageNumber>=totalPages){
            model.addAttribute("pageNumber", 0);
        } else {
            model.addAttribute("pageNumber", pageNumber);
        }
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("books",result);
        model.addAttribute("totalPages", totalPages);
    }

}
