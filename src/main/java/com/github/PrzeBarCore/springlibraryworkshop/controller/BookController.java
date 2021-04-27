package com.github.PrzeBarCore.springlibraryworkshop.controller;

import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookEditionReqBookEditionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookEditionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookRespBookDTO;
import com.github.PrzeBarCore.springlibraryworkshop.service.BookEditionService;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
class BookController {
    private final BookService service;
    private final BookEditionService editionService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final String className;

    BookController(final BookService service, final BookEditionService editionService) {
        this.service= service;
        this.editionService=editionService;
        this.className= this.getClass().getSimpleName();
    }

    @PostMapping
    @Transactional
    String createBook(@ModelAttribute("bookToCreate") @Valid BookReqBookDTO toCreate, Model model){

        logger.info("Creating book. FROM"+className);
        model.addAttribute("book",service.createBook(toCreate));
        return "bookDisplay";
    }

    @PostMapping(params = "addAuthor")
    String addAuthor(@Valid @ModelAttribute("bookToCreate") BookReqBookDTO current){

        logger.info("Adding author in book to create. FROM "+className);
        current.addAuthor();
        return "bookForm";
    }

    @PostMapping(params="removeAuthor")
    String removeAuthor(@Valid @ModelAttribute("bookToCreate") BookReqBookDTO current,
                        @RequestParam("removeAuthor") int authorIndexToRemove ){

        logger.info("Removing author in book to create. FROM "+className);
        current.removeAuthor(authorIndexToRemove);
        return "bookForm";
    }

    @PostMapping(params = "addBookCopy")
    String addBookEdition(@Valid @ModelAttribute("bookToCreate") BookReqBookDTO current){

        logger.info("Adding bookCopy in book to create. FROM "+className);
        current.addBookEdition();
        return "bookForm";
    }

    @PostMapping(params="removeBookCopy")
    String removeBookEdition(@Valid @ModelAttribute("bookToCreate") BookReqBookDTO current,
                             @RequestParam("removeBookCopy") int copyIndexToRemove ){

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

    @GetMapping(value="/update/{id}")
    String showBookUpdateForm(@PathVariable int id, Model model){

        model.addAttribute("bookToUpdate",service.getBookWriteModelById(id));
        return "bookUpdateForm";
    }

    @PostMapping("/update/{id}")
    @Transactional
    String updateBook(@Valid @ModelAttribute("bookToUpdate") BookReqBookDTO bookToUpdate,
                      @PathVariable int id, Model model){

        logger.info("Updating book. FROM "+className);
        model.addAttribute("book", service.updateBook(bookToUpdate,id));
        return "bookDisplay";
    }

    @PostMapping(value="/update/{id}",params = "addAuthor")
    String addAuthorOfBookToUpdate( @ModelAttribute("bookToUpdate") BookReqBookDTO current, @PathVariable int id){

        logger.info("Adding author in book to update. FROM "+className);
        current.addAuthor();
        return "bookUpdateForm";
    }

    @PostMapping(value="/update/{id}",params="removeAuthor")
    String removeAuthorOfBookToUpdate(@Valid @ModelAttribute("bookToUpdate") BookReqBookDTO current,
                                      @RequestParam("removeAuthor") int authorIndexToRemove){

        logger.info("Removing author in book to update. FROM "+className);
        current.removeAuthor(authorIndexToRemove);
        return "bookUpdateForm";
    }

    @PostMapping("/delete/{id}")
    @Transactional
    String deleteBook(@PathVariable Integer id, Model model){
        service.deleteBook(id);
        return "books";
    }

    @GetMapping(value="/{bookId}/addEdition")
    String showNewBookEditionForm(@PathVariable int bookId, Model model){
        model.addAttribute("editionToCreate",new BookReqBookEditionDTO());
        model.addAttribute("sourceBookId",bookId);
        return "newBookEditionForm";
    }

    @PostMapping(value="/{bookId}/addEdition")
    String addNewBookEdition(@Valid @ModelAttribute BookReqBookEditionDTO editionToCreate, @PathVariable int bookId, Model model){
        model.addAttribute("book", service.addNewEdition(editionToCreate,bookId));
        return "bookDisplay";
    }

    @GetMapping("/{bookId}/editionUpdate/{editionId}")
    String showBookEditionUpdateForm( @PathVariable int editionId, @PathVariable int bookId, Model model){

        model.addAttribute("bookEditionToUpdate",editionService.getBookEditionWriteModelById(editionId));
        model.addAttribute("sourceBookId", bookId);
        return "bookEditionUpdateForm";
    }


    @PostMapping("/{bookId}/editionUpdate/{editionId}")
    @Transactional
    String updateBookEdition(@Valid @ModelAttribute("bookEditionToUpdate") BookEditionReqBookEditionDTO current,
                             @PathVariable int bookId, @PathVariable int editionId, Model model){

        editionService.updateEdition(current, editionId);
        return readBook(bookId,model);
    }

    @PostMapping(value="/{bookId}/editionUpdate/{editionId}", params="addBookCopy")
    String bookEditionUpdateFormAddCopy(@Valid @ModelAttribute("bookEditionToUpdate") BookEditionReqBookEditionDTO current,
                                        @PathVariable int bookId, Model model){

        current.addBookCopy();
        model.addAttribute("bookEditionToUpdate",current);
        model.addAttribute("sourceBookId", bookId);
        return "bookEditionUpdateForm";
    }

    @PostMapping(value="/{bookId}/editionUpdate/{editionId}", params="removeBookCopy")
    String bookEditionUpdateFormRemoveCopy(@Valid @ModelAttribute("bookEditionToUpdate") BookEditionReqBookEditionDTO current,
                                           @PathVariable int bookId, @RequestParam("removeBookCopy") int copyToRemove,
                                           Model model){

        current.removeBookCopy(copyToRemove);
        model.addAttribute("bookEditionToUpdate",current);
        model.addAttribute("sourceBookId", bookId);
        return "bookEditionUpdateForm";
    }

    @PostMapping(value="/{bookId}/deleteEdition/{editionId}")
    @Transactional
    String deleteBookEdition(@PathVariable int bookId, @PathVariable int editionId, Model model){
        service.deleteBookEdition(bookId,editionId);
        logger.info("Deleting edition");
        model.addAttribute("book", service.getBookReadModelById(bookId));
        return "bookDisplay";
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
