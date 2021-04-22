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

    public BookRespBookDTO updateBook(BookReqBookDTO toUpdate, Integer id){
        Book book=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Book with given ID doesn't exist"));

        createOrUpdateBook(toUpdate,book);

        return new BookRespBookDTO(book);
    }

    public void deleteBook(Integer id) {
        if(!repository.existsBookById(id)){
            throw new IllegalArgumentException("Book with given ID doesn't exist");
        }

        repository.deleteBookById(id);
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
        repository.save(book);
    }

    ///Should it be here?
    public void reserveBookCopy(Integer id){

        BookCopy result=bookCopyRepository.findBookCopyById(id).orElseThrow(()->new IllegalArgumentException("Book Copy doesn't exist"));
        LocalDateTime borrowedUntil=result.getBorrowedUntil();
        if(borrowedUntil==null){
            result.setReservedUntil(LocalDateTime.now().plusMonths(1));
            result.setState(2);
        } else {
            result.setReservedUntil(borrowedUntil.plusMonths(1));
        }
        bookCopyRepository.save(result);
    }
    public Page<BookRespBookDTO> readAllBooks(Pageable pageable) {
        Page<Book> result= repository.findAll(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }
        return result.map(BookRespBookDTO::new);
    }

}
