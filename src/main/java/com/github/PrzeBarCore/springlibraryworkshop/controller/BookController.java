package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookRespBookDTO;
import com.github.PrzeBarCore.springlibraryworkshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
class BookController {
    private final BookService service;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final String className;

    BookController(final BookService service) {
        this.service= service;
        this.className= this.getClass().getSimpleName();
    }

    @PostMapping
    @Transactional
    String createBook(@ModelAttribute("bookToCreate") BookReqBookDTO toCreate){
        logger.info("Creating book. FROM"+className);
        service.createBook(toCreate);
        return "books";
    }

    @PostMapping(params = "addAuthor")
    String addAuthor(@ModelAttribute("bookToCreate") BookReqBookDTO current){
        logger.info("Adding author in book to create. FROM "+className);
        current.addAuthor();
        return "bookForm";
    }

    @PostMapping(params="removeAuthor")
    String removeAuthor(@ModelAttribute("bookToCreate") BookReqBookDTO current, @RequestParam("removeAuthor") int authorIndexToRemove ){
        logger.info("Removing author in book to create. FROM "+className);
        current.removeAuthor(authorIndexToRemove);
        return "bookForm";
    }

    @PostMapping(params = "addBookCopy")
    String addBookEdition(@ModelAttribute("bookToCreate") BookReqBookDTO current){
        logger.info("Adding bookCopy in book to create. FROM "+className);
        current.addBookEdition();
        return "bookForm";
    }

    @PostMapping(params="removeBookCopy")
    String removeBookEdition(@ModelAttribute("bookToCreate") BookReqBookDTO current, @RequestParam("removeBookCopy") int copyIndexToRemove ){
        logger.info("Removing bookCopy in book to create. FROM "+className);
        current.removeBookEdition(copyIndexToRemove);
        return "bookForm";
    }

    @GetMapping
    String readAllBooks(){
        logger.info("Reading all books. FROM "+className);
        return "books";
    }


    @GetMapping("/{id}")
    String readBook(@PathVariable Integer id, Model model){
        logger.info("Reading book. FROM "+className);
        BookRespBookDTO book= service.getBookReadModelById(id);
        model.addAttribute("book", book);
        return "bookDisplay";
    }

    @PostMapping(value="{bookId}/changeStatus/{bookCopyId}" ,params="state")
    String reserveBookCopy(@PathVariable("bookCopyId") Integer bookCopyId,
                           @PathVariable("bookId") Integer bookId,
                           Model model, @RequestParam("state") String state) {
        service.changeStatus(bookCopyId, state);
        BookRespBookDTO book= service.getBookReadModelById(bookId);
        model.addAttribute("book", book);
        return "bookDisplay";
    }


    @GetMapping("/new")
    String showBookForm(Model model){
        BookReqBookDTO book= new BookReqBookDTO();
        model.addAttribute("bookToCreate",book);
        return "bookForm";
    }

    @GetMapping("/update/{id}")
    String showBookUpdateForm(@PathVariable int id, Model model){
        model.addAttribute("bookToUpdate",service.getBookWriteModelById(id));
        return "bookUpdateForm";
    }

    @PostMapping("/update/{id}")
    String updateBook(@PathVariable int id, @ModelAttribute("bookToUpdate") BookReqBookDTO bookToUpdate, Model model){
        logger.info("Updating book. FROM "+className);
        service.updateBook(bookToUpdate,id);
        model.addAttribute("book", service.getBookReadModelById(id));
        return "bookDisplay";
    }

    @PostMapping(value="/update/{id}",params = "addAuthor")
    String addAuthorOfBookToUpdate(@ModelAttribute("bookToUpdate") BookReqBookDTO current, @PathVariable int id){
        logger.info("Adding author in book to update. FROM "+className);
        current.addAuthor();
        return "bookUpdateForm";
    }

    @PostMapping(value="/update/{id}",params="removeAuthor")
    String removeAuthorOfBookToUpdate(@ModelAttribute("bookToUpdate") BookReqBookDTO current, @RequestParam("removeAuthor") int authorIndexToRemove, @PathVariable int id ){
        logger.info("Removing author in book to update. FROM "+className);
        current.removeAuthor(authorIndexToRemove);
        return "bookUpdateForm";
    }



    @PostMapping("/delete/{id}")
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
