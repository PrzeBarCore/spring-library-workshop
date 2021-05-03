package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.dao.BookCopyRepository;
import com.github.PrzeBarCore.springlibraryworkshop.dao.BookRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.*;
import com.github.PrzeBarCore.springlibraryworkshop.utils.BookState;
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
    private final Logger logger= LoggerFactory.getLogger(BookService.class);
    private final SectionService sectionService;
    private final BookRepository repository;
    private final AuthorService authorService;
    private final BookEditionService editionService;
    private final BookCopyRepository bookCopyRepository;

    public BookService(final BookRepository repository, final AuthorService authorService,
                       final BookEditionService editionService, final SectionService sectionService,
                       final BookCopyRepository copyRepository) {
        this.sectionService = sectionService;
        this.editionService = editionService;
        this.authorService = authorService;
        this.repository = repository;
        this.bookCopyRepository=copyRepository;
    }

    @Transactional
    public BookRespBookDTO createBook(BookReqBookDTO toCreate) {
        var book = new Book();
        book.setTitle(toCreate.getTitle());

        BookReqSectionDTO newSection= toCreate.getSection();
        Integer sectionId= newSection.getId();
        if (newSection.isNewSection()) {
            sectionService.throwExceptionIfSectionNameIsTaken(newSection.getName(), sectionId);
            book.setSection(newSection.toSection(book));
        } else {
            book.setSection(sectionService.
                    readSectionById(sectionId));
        }
        createAndAddBookAuthors(toCreate, book);

        Set<BookEdition> bookEditions= new HashSet<>();
        toCreate.getBookEditions()
                .forEach(bookEdition -> bookEditions.add(editionService.createBookEdition(bookEdition,book,true)));
        book.setBookEditions(bookEditions);

        return new BookRespBookDTO(repository.save(book));
    }

    public Page<BookRespBookDTO> readAllBooks(Pageable pageable) {
        Page<Book> result= repository.findAll(pageable);

        if(pageable.getPageNumber()>=result.getTotalPages()){
            result=repository.findAll(pageable.first());
        }
        return result.map(BookRespBookDTO::new);
    }

    public Book readBookById(int id){
        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Book with given ID doesn't exist"));
    }

    public BookRespBookDTO getBookReadModelById(Integer id) {
        return new BookRespBookDTO(readBookById(id));
    }

    public BookReqBookDTO getBookWriteModelById(Integer id) {
        return new BookReqBookDTO(readBookById(id));
    }

    @Transactional
    public BookRespBookDTO updateBook(BookReqBookDTO toUpdate, Integer id){
        Book book=readBookById(id);
        Integer currentSectionId= book.getSection().getId();
        
        book.setTitle(toUpdate.getTitle());

        book.setSection(sectionService.createSection(toUpdate.getSection(), book));

        createAndAddBookAuthors(toUpdate, book);
        repository.save(book);

        toUpdate.getAuthorsToRemove().stream()
                .map(BookReqAuthorDTO::getId).
                forEach(authorService::deleteAuthorIfListOfBooksIsEmpty);
        sectionService.deleteSectionIfListOfBooksIsEmpty(currentSectionId);
        logger.info("Updating book");

        return new BookRespBookDTO(book);
    }


    @Transactional
    public void deleteBook(Integer id) {
        Book bookToDelete= readBookById(id);

        int sectionId=bookToDelete.getSection().getId();
        Set<Author> authors=bookToDelete.getAuthors();
        Set<Integer> authorsIds=authors.stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
        Set<Integer> editionsIds=bookToDelete.getBookEditions().stream()
                .map(BookEdition::getId)
                .collect(Collectors.toSet());

        bookToDelete.getSection().getBooks().remove(bookToDelete);
        authors.forEach(author-> author.getBooks().remove(bookToDelete));

        bookToDelete.getBookEditions().clear();
        editionsIds.forEach(editionService::deleteBookEditionById);

        repository.deleteById(id);

        authorsIds.forEach(authorService::deleteAuthorIfListOfBooksIsEmpty);
        sectionService.deleteSectionIfListOfBooksIsEmpty(sectionId);
    }

    public BookRespBookDTO addNewEdition(BookReqBookEditionDTO editionToCreate, int bookId){
        Book book=readBookById(bookId);
        book.getBookEditions().add(
                editionService.createBookEdition(editionToCreate,book,false));
        return new BookRespBookDTO(repository.save(book));
    }

    @Transactional
    public void deleteBookEdition(int bookId, int editionId) {
        Book book= readBookById(bookId);
        if(book.getBookEditions().size()>1){
            book.getBookEditions().remove(editionService.findBookEditionById(editionId));
            editionService.deleteBookEditionById(editionId);
        } else {
            throw new IllegalArgumentException("Cannot delete last edition of book");
        }
        repository.save(book);
    }

    public void changeStatus(Integer id, String state) {
        switch (state) {
            case "reserve":
                reserveBookCopy(id);
                break;
            case "lend":
                lendBookCopy(id);
                break;
            case "return":
                returnBookCopy(id);
                break;
            default:
                cancelReservationOfBookCopy(id);
                break;
        }
    }
    private BookCopy readBookCopy(Integer id){
        return bookCopyRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Book Copy doesn't exist"));
    }
    ///Should it be here?

    private void cancelReservationOfBookCopy(Integer id) {
        BookCopy result=readBookCopy(id);
        if(result.getReservedUntil()!=null){
            result.setReservedUntil(null);
            if(result.getState()== BookState.RESERVED){
                result.setState(BookState.AVAILABLE);
            }
            bookCopyRepository.save(result);
        }
    }

    private void returnBookCopy(Integer id) {
        BookCopy result=readBookCopy(id);
        if(result.getState()==BookState.BORROWED){
            if(result.getReservedUntil()!=null){
                result.setState(BookState.RESERVED);
            } else {
                result.setState(BookState.AVAILABLE);
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
                result.setState(BookState.RESERVED);
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
        if(result.getState()==BookState.BORROWED){
            throw new IllegalArgumentException("This book is already lent");
        }
        result.setState(BookState.BORROWED);
        result.setReservedUntil(null);
        result.setBorrowedUntil(LocalDateTime.now().plusMonths(1));
        bookCopyRepository.save(result);
    }

    private void createAndAddBookAuthors(BookReqBookDTO toCreate, Book book) {
        toCreate.getAuthors().stream()
                .filter(BookReqAuthorDTO::isNewAuthor)
                .forEach(author -> {
                    var newAuthor = authorService.createAuthor(author, book);
                    author.setId(newAuthor.getId());
                });
        book.setAuthors(toCreate.getAuthors().stream()
                .map(author -> authorService.readAuthorById(author.getId()))
                .collect(Collectors.toSet()));
        logger.info("Creating or updating book. FROM"+this.getClass().getSimpleName());
    }
}
