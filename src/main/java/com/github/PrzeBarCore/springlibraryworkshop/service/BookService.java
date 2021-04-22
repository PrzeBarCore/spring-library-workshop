package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookCopyRepository;
import com.github.PrzeBarCore.springlibraryworkshop.dao.BookRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.*;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookRespBookDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqSectionDTO;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReqBookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    public static final Logger logger= LoggerFactory.getLogger(BookService.class);
    private final SectionService sectionService;
    private final BookRepository repository;
    private final AuthorService authorService;
    private final BookEditionService bookEditionService;
    private final BookCopyRepository bookCopyRepository;

    public BookService(final BookRepository repository, final AuthorService authorService,
                       final BookEditionService bookEditionService, final SectionService sectionService,
                        final BookCopyRepository bookCopyRepository) {
        this.sectionService = sectionService;
        this.bookEditionService = bookEditionService;
        this.authorService = authorService;
        this.repository = repository;
        this.bookCopyRepository=bookCopyRepository;
    }
    @Transactional
    public BookRespBookDTO createBook(BookReqBookDTO toCreate) {
        var book = new Book();
        createOrUpdateBook(toCreate,book);

        Set<BookEdition> bookEditions= new HashSet<>();
        toCreate.getBookEditions()
                .forEach(bookCopy -> bookEditions.add(bookEditionService.createBookEdition(bookCopy,book)));
        book.setBookEditions(bookEditions);

        return new BookRespBookDTO(book);
    }

    public BookRespBookDTO getBookReadModelById(Integer id) {
        BookRespBookDTO result= repository.findById(id)
                .map(BookRespBookDTO::new)
                .orElseThrow(() ->new IllegalArgumentException("Book with given ID doesn't exist"));

        return result;
    }

    public BookReqBookDTO getBookWriteModelById(Integer id) {
        BookReqBookDTO result= repository.findById(id)
                .map(BookReqBookDTO::fromBook)
                .orElseThrow(() ->new IllegalArgumentException("Book with given ID doesn't exist"));
        return result;
    }

    public BookRespBookDTO updateBook(BookReqBookDTO toUpdate, Integer id){
        Book book=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Book with given ID doesn't exist"));

        createOrUpdateBook(toUpdate,book);
        logger.info("Updating book");
        return new BookRespBookDTO(book);
    }

    ///TODO
    @Transactional
    public void deleteBook(Integer id) {
        Book bookToDelete= repository.findById(id).
                orElseThrow(()->new IllegalArgumentException("Book with given ID doesn't exist"));

        bookToDelete.getBookEditions()
                        .forEach(bookEdition -> bookEdition.getBookCopies()
                                .forEach(bookCopy -> {
                                    if(bookCopy.getState()!=0)
                                        throw new IllegalArgumentException("Not every book copy is available. Removal is impossible.");
                                })
                        );

        bookToDelete.getBookEditions().forEach(bookEditionService::deleteBookEdition);

        repository.deleteBookById(id);
        logger.info("deleted book");
        bookToDelete.getAuthors()
                .forEach(author->authorService.deleteAuthor(author.getId()));
        logger.info("deleted authors");
    }

    private void createOrUpdateBook(BookReqBookDTO toCreate, Book book) {
        book.setTitle(toCreate.getTitle());

        BookReqSectionDTO section= toCreate.getSection();
        if (section.isNewSection()) {
            int id= sectionService.createSectionAndGetId(book, section);
            section.setId(id);
        }
        book.setSection(sectionService.readSectionById(section.getId()));

        toCreate.getAuthors().stream()
                .filter(author -> author.isNewAuthor())
                .forEach(author -> {
                    var newAuthor = authorService.createAuthor(author, book);
                    author.setId(newAuthor.getId());
                });
        book.setAuthors(toCreate.getAuthors().stream()
                .map(author -> authorService.readAuthorById(author.getId()))
                .collect(Collectors.toSet()));
        logger.info("Creating or updating book. FROM"+this.getClass().getSimpleName());
        repository.save(book);
    }


    public Page<BookRespBookDTO> readAllBooks(Pageable pageable) {
        Page<Book> result= repository.findAll(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }
        return result.map(BookRespBookDTO::new);
    }

    public void changeStatus(Integer id, String state) {
        if(state.equals("reserve")){
            reserveBookCopy(id);
        } else if(state.equals("lend")){
            lendBookCopy(id);
        } else if(state.equals("return")){
            returnBookCopy(id);
        } else {
            cancelReservationOfBookCopy(id);
        }
    }
    private BookCopy readBookCopy(Integer id){
        return bookCopyRepository.findBookCopyById(id).orElseThrow(()->new IllegalArgumentException("Book Copy doesn't exist"));
    }
    ///Should it be here?
    private void cancelReservationOfBookCopy(Integer id) {
        BookCopy result=readBookCopy(id);
        if(result.getReservedUntil()!=null){
            result.setReservedUntil(null);
            if(result.getState()==2){
                result.setState(0);
            }
            bookCopyRepository.save(result);
        }
    }

    private void returnBookCopy(Integer id) {
        BookCopy result=readBookCopy(id);
        if(result.getState()==1){
            if(result.getReservedUntil()!=null){
                result.setState(2);
            } else {
                result.setState(0);
            }
            result.setBorrowedUntil(null);
            bookCopyRepository.save(result);

        } else {
            throw new IllegalArgumentException("Book have to be on loan at first to be returned");
        }
    }

    private void reserveBookCopy(Integer id){

        BookCopy result=readBookCopy(id);
        LocalDateTime borrowedUntil=result.getBorrowedUntil();
        if(result.getReservedUntil()==null){
            if(borrowedUntil==null){
                result.setReservedUntil(LocalDateTime.now().plusMonths(1));
                result.setState(2);
            } else {
                result.setReservedUntil(borrowedUntil.plusMonths(1));
            }
            bookCopyRepository.save(result);
        } else {
            throw new IllegalArgumentException("Book cannot be reserved twice!");
        }

    }
    private void lendBookCopy(Integer id) {
        BookCopy result=readBookCopy(id);
        if(result.getState()==1){
            throw new IllegalArgumentException("Book have to be reserved or available to lend it");
        }
        result.setState(1);
        result.setReservedUntil(null);
        result.setBorrowedUntil(LocalDateTime.now().plusMonths(1));
        bookCopyRepository.save(result);
    }
}
