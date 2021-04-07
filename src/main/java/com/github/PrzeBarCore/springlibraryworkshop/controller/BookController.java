package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReadModel;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookWriteModel;
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
    public String createBook(@ModelAttribute("bookToCreate") BookWriteModel toCreate){
        service.createBook(toCreate).getId();
        return "books";
    }

    @PostMapping(params = "addAuthor")
    public String addAuthor(@ModelAttribute("bookToCreate") BookWriteModel current){
        current.addAuthor();
        return "bookForm";
    }

    @PostMapping(params="removeAuthor")
    public String removeAuthor(@ModelAttribute("bookToCreate") BookWriteModel current, @RequestParam("removeAuthor") int authorIndexToRemove ){
        current.removeAuthor(authorIndexToRemove);
        return "bookForm";
    }

    @PostMapping(params = "addBookCopy")
    public String addBookCopy(@ModelAttribute("bookToCreate") BookWriteModel current){
        current.addBookCopy();
        return "bookForm";
    }

    @PostMapping(params="removeBookCopy")
    public String removeBookCopy(@ModelAttribute("bookToCreate") BookWriteModel current, @RequestParam("removeBookCopy") int copyIndexToRemove ){
        current.removeBookCopy(copyIndexToRemove);
        return "bookForm";
    }

    @GetMapping
    public String readAllBooks(){
        return "books";
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookReadModel> readBook(@PathVariable Integer id){
        return ResponseEntity.ok(service.readBook(id));
    }

    @GetMapping("/new")
    public String showBookForm(Model model){
        BookWriteModel book= new BookWriteModel();
        model.addAttribute("bookToCreate",book);
        return "bookForm";
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BookReadModel> updateBook(@RequestBody BookWriteModel toUpdate, @PathVariable Integer id){
        return ResponseEntity.ok(service.updateBook(toUpdate,id));
    }

    @PostMapping("/{id}")
    @Transactional
    public String deleteBook(@PathVariable Integer id){
        service.deleteBook(id);
        return "books";

    }

    @ModelAttribute
    void getAllBooks( @PageableDefault(size=10) Pageable pageable, Model model){
        Page<Book> result=service.readAllBooks(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=service.readAllBooks(pageable.first());
            model.addAttribute("pageNumber", 0);
        } else {
            model.addAttribute("pageNumber", pageable.getPageNumber());
        }
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("books",service.mapBooks(result));
        model.addAttribute("totalPages", result.getTotalPages());
    }

}
