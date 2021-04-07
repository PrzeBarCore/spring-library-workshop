package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookReadModel;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookSectionWriteModel;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.BookWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    public static final Logger logger= LoggerFactory.getLogger(BookService.class);
    private final SectionService sectionService;
    private final BookRepository repository;
    private final AuthorService authorService;
    private final BookCopyService bookCopyService;
    public BookService(final BookRepository repository, final AuthorService authorService,
                       final BookCopyService bookCopyService, final SectionService sectionService) {
        this.sectionService = sectionService;
        this.bookCopyService= bookCopyService;
        this.authorService = authorService;
        this.repository = repository;
    }
    @Transactional
    public BookReadModel createBook(BookWriteModel toCreate) {
        var book = new Book();
        createOrUpdateBook(toCreate,book);

        Set<BookCopy> createdBookCopies= new HashSet<>();
        toCreate.getBookCopies()
                .forEach(bookCopy -> createdBookCopies.add(bookCopyService.createBookCopy(bookCopy,book)));
        book.setBookCopies(createdBookCopies);

        return new BookReadModel(book);
    }

    public BookReadModel readBook(Integer id) {
        BookReadModel result= repository.findById(id)
                .map(BookReadModel::new)
                .orElseThrow(() ->new IllegalArgumentException("Book with given ID doesn't exist"));
        return result;
    }

    public BookReadModel updateBook(BookWriteModel toUpdate, Integer id){
        Book book=repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Book with given ID doesn't exist"));

        createOrUpdateBook(toUpdate,book);

        return new BookReadModel(book);
    }

    public void deleteBook(Integer id) {
        if(!repository.existsBookById(id)){
            throw new IllegalArgumentException("Book with given ID doesn't exist");
        }

        repository.deleteBookById(id);
    }

    private void createOrUpdateBook(BookWriteModel toCreate, Book book) {
        book.setTitle(toCreate.getTitle());

        BookSectionWriteModel section= toCreate.getSection();
        if (section.isNewSection()) {
            int id= sectionService.createSectionAndGetId(book, section);
            section.setId(id);
        }
        book.setSection(sectionService.findSection(section.getId()));

        toCreate.getAuthors().stream()
                .filter(author -> author.isNewAuthor())
                .forEach(author -> {
                    var newAuthor = authorService.createAuthor(author, book);
                    author.setId(newAuthor.getId());
                });
        book.setAuthors(toCreate.getAuthors().stream()
                .map(author -> authorService.readAuthor(author.getId()))
                .collect(Collectors.toSet()));
        repository.save(book);
    }

    public Page<Book> readAllBooks(Pageable pageable) {
        Page<Book> result= repository.findAll(pageable);
        return result;
    }

    public List<BookReadModel> mapBooks(Page<Book> books) {
        List<BookReadModel> result=books
                .stream()
                .map(BookReadModel::new)
                .collect(Collectors.toList());
        return result;
    }
}
