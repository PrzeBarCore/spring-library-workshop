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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        this.service = service;
        this.editionService = editionService;
        this.className = this.getClass().getSimpleName();
    }

    @PostMapping
    @Transactional
    String createBook(@ModelAttribute("bookToCreate") @Valid BookReqBookDTO toCreate,
                      BindingResult bindingResult,
                      Model model) {
        logger.info("Creating book. FROM" + className);
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", toCreate);
            model.addAttribute(bindingResult);
            return "bookForm";
        } else {
            try {
                model.addAttribute("book", service.createBook(toCreate));
                return "bookDisplay";
            } catch (IllegalArgumentException e) {
                bindingResult.reject("error.book", e.getMessage());
                model.addAttribute("book", toCreate);
                model.addAttribute(bindingResult);
                return "bookForm";
            }
        }
    }

    @PostMapping(params = "addAuthor")
    String addAuthor(@ModelAttribute("bookToCreate") @Valid BookReqBookDTO current) {

        logger.info("Adding author in book to create. FROM " + className);
        current.addAuthor();
        return "bookForm";
    }

    @PostMapping(params = "removeAuthor")
    String removeAuthor(@ModelAttribute("bookToCreate") @Valid BookReqBookDTO current,
                        @RequestParam("removeAuthor") int authorIndexToRemove) {

        logger.info("Removing author in book to create. FROM " + className);
        current.removeAuthor(authorIndexToRemove);
        return "bookForm";
    }

    @PostMapping(params = "addBookCopy")
    String addBookEdition(@ModelAttribute("bookToCreate") @Valid BookReqBookDTO current) {

        logger.info("Adding bookCopy in book to create. FROM " + className);
        current.addBookEdition();
        return "bookForm";
    }

    @PostMapping(params = "removeBookCopy")
    String removeBookEdition(@ModelAttribute("bookToCreate") @Valid BookReqBookDTO current,
                             @RequestParam("removeBookCopy") int copyIndexToRemove) {

        logger.info("Removing bookCopy in book to create. FROM " + className);
        current.removeBookEdition(copyIndexToRemove);
        return "bookForm";
    }

    @GetMapping
    String readAllBooks() {
        logger.info("Reading all books. FROM " + className);
        return "books";
    }


    @GetMapping("/{id}")
    String readBook(@PathVariable Integer id, Model model) {
        logger.info("Reading book. FROM " + className);
        BookRespBookDTO book = service.getBookReadModelById(id);
        model.addAttribute("book", book);
        return "bookDisplay";
    }

    @PostMapping(value = "{bookId}/changeStatus/{bookCopyId}", params = "state")
    String reserveBookCopy(@PathVariable("bookCopyId") Integer bookCopyId,
                           @PathVariable("bookId") Integer bookId,
                           Model model, @RequestParam("state") String state) {
        service.changeStatus(bookCopyId, state);
        BookRespBookDTO book = service.getBookReadModelById(bookId);
        model.addAttribute("book", book);
        return "bookDisplay";
    }


    @GetMapping("/new")
    String showBookForm(Model model) {
        BookReqBookDTO book = new BookReqBookDTO();
        model.addAttribute("bookToCreate", book);
        return "bookForm";
    }

    @GetMapping(value = "/update/{id}")
    String showBookUpdateForm(@PathVariable int id, Model model) {

        model.addAttribute("bookToUpdate", service.getBookWriteModelById(id));
        return "bookUpdateForm";
    }

    @PostMapping("/update/{id}")
    @Transactional
    String updateBook(@ModelAttribute("bookToUpdate") @Valid BookReqBookDTO bookToUpdate,
                      BindingResult bindingResult,
                      @PathVariable int id, Model model) {

        logger.info("Updating book. FROM " + className);
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookToUpdate", bookToUpdate);
            model.addAttribute(bindingResult);
            return "bookUpdateForm";
        } else {
            try {
                model.addAttribute("book", service.updateBook(bookToUpdate, id));
                return "bookDisplay";
            } catch (IllegalArgumentException e) {
                bindingResult.reject("error.bookToUpdate", e.getMessage());
                model.addAttribute("bookToUpdate", bookToUpdate);
                model.addAttribute(bindingResult);
                return "bookUpdateForm";
            }
        }
    }

    @PostMapping(value = "/update/{id}", params = "addAuthor")
    String addAuthorOfBookToUpdate(@ModelAttribute("bookToUpdate") BookReqBookDTO current, @PathVariable int id) {

        logger.info("Adding author in book to update. FROM " + className);
        current.addAuthor();
        return "bookUpdateForm";
    }

    @PostMapping(value = "/update/{id}", params = "removeAuthor")
    String removeAuthorOfBookToUpdate(@ModelAttribute("bookToUpdate") @Valid BookReqBookDTO current,
                                      @RequestParam("removeAuthor") int authorIndexToRemove) {

        logger.info("Removing author in book to update. FROM " + className);
        current.removeAuthor(authorIndexToRemove);
        return "bookUpdateForm";
    }

    @PostMapping("/delete/{id}")
    @Transactional
    String deleteBook(@PathVariable Integer id, Model model) {
        try{
            service.deleteBook(id);
        } catch (IllegalArgumentException e){
            model.addAttribute("book", service.getBookReadModelById(id));
            model.addAttribute("errorMessage", e.getMessage());
            return "bookDisplay";
        }
        getAllBooks(PageRequest.of(0,10),model);
        return "books";
    }

    @GetMapping(value = "/{bookId}/addEdition")
    String showNewBookEditionForm(@PathVariable int bookId, Model model) {
        model.addAttribute("editionToCreate", new BookReqBookEditionDTO());
        model.addAttribute("sourceBookId", bookId);
        return "newBookEditionForm";
    }

    @PostMapping(value = "/{bookId}/addEdition")
    String addNewBookEdition(@ModelAttribute("editionToCreate") @Valid BookReqBookEditionDTO editionToCreate,
                             BindingResult bindingResult,
                             @PathVariable int bookId, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editionToCreate", editionToCreate);
            model.addAttribute("sourceBookId", bookId);
            model.addAttribute(bindingResult);
            return "newBookEditionForm";
        } else {
            try {
                model.addAttribute("book", service.addNewEdition(editionToCreate, bookId));
                return "bookDisplay";
            } catch (IllegalArgumentException e) {
                bindingResult.reject("error.editionToCreate", e.getMessage());
                model.addAttribute("editionToCreate", editionToCreate);
                model.addAttribute("sourceBookId", bookId);
                model.addAttribute(bindingResult);
                return "newBookEditionForm";
            }
        }
    }

    @GetMapping("/{bookId}/editionUpdate/{editionId}")
    String showBookEditionUpdateForm(@PathVariable int editionId, @PathVariable int bookId, Model model) {

        model.addAttribute("bookEditionToUpdate", editionService.getBookEditionWriteModelById(editionId));
        model.addAttribute("sourceBookId", bookId);
        return "bookEditionUpdateForm";
    }


    @PostMapping("/{bookId}/editionUpdate/{editionId}")
    @Transactional
    String updateBookEdition(@ModelAttribute("bookEditionToUpdate") @Valid BookEditionReqBookEditionDTO current,
                             BindingResult bindingResult,
                             @PathVariable int bookId, @PathVariable int editionId, Model model) {
        if (bindingResult.hasErrors()) {
            current.setId(editionId);
            model.addAttribute("bookEditionToUpdate", current);
            model.addAttribute("sourceBookId", bookId);
            model.addAttribute(bindingResult);
            return "bookEditionUpdateForm";
        } else {
            try {
                editionService.updateBookEdition(current, editionId);
                return readBook(bookId, model);
            } catch (IllegalArgumentException e) {
                current.setId(editionId);
                bindingResult.reject("error.bookEditionToUpdate", e.getMessage());
                model.addAttribute("bookEditionToUpdate", current);
                model.addAttribute("sourceBookId", bookId);
                model.addAttribute(bindingResult);
                return "bookEditionUpdateForm";
            }
        }
    }

    @PostMapping(value = "/{bookId}/editionUpdate/{editionId}", params = "addBookCopy")
    String bookEditionUpdateFormAddCopy(@ModelAttribute("bookEditionToUpdate") @Valid BookEditionReqBookEditionDTO current,
                                        @PathVariable int bookId, Model model) {

        current.addBookCopy();
        model.addAttribute("bookEditionToUpdate", current);
        model.addAttribute("sourceBookId", bookId);
        return "bookEditionUpdateForm";
    }

    @PostMapping(value = "/{bookId}/editionUpdate/{editionId}", params = "removeBookCopy")
    String bookEditionUpdateFormRemoveCopy(@ModelAttribute("bookEditionToUpdate") @Valid BookEditionReqBookEditionDTO current,
                                           BindingResult bindingResult,
                                           @PathVariable int bookId, @RequestParam("removeBookCopy") int copyToRemove,
                                           Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("bookEditionToUpdate", current);
            model.addAttribute("sourceBookId", bookId);
            model.addAttribute(bindingResult);
            return "bookEditionUpdateForm";
        } else {
            try{
                current.removeBookCopy(copyToRemove);
                model.addAttribute("bookEditionToUpdate", current);
                model.addAttribute("sourceBookId", bookId);
                return "bookEditionUpdateForm";
            } catch (IllegalArgumentException e){
                bindingResult.reject("bookEditionToUpdate", e.getMessage());
                model.addAttribute("bookEditionToUpdate", current);
                model.addAttribute("sourceBookId", bookId);
                model.addAttribute(bindingResult);
                return "bookEditionUpdateForm";
            }
        }
    }

    @PostMapping(value = "/{bookId}/deleteEdition/{editionId}")
    @Transactional
    String deleteBookEdition(@PathVariable int bookId, @PathVariable int editionId, Model model) {
        logger.info("Deleting edition");
        try{
            service.deleteBookEdition(bookId, editionId);
            model.addAttribute("book", service.getBookReadModelById(bookId));
            return "bookDisplay";
        } catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("book", service.getBookReadModelById(bookId));
            return "bookDisplay";
        }
    }

    @ModelAttribute
    void getAllBooks(@PageableDefault(size = 10) Pageable pageable, Model model) {

        Page<BookRespBookDTO> result = service.readAllBooks(pageable);
        int pageNumber = pageable.getPageNumber();
        int totalPages = result.getTotalPages();

        if (pageNumber >= totalPages) {
            model.addAttribute("pageNumber", 0);
        } else {
            model.addAttribute("pageNumber", pageNumber);
        }
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("books", result);
        model.addAttribute("totalPages", totalPages);
    }
}
