package com.github.PrzeBarCore.springlibraryworkshop.service;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookRepository;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookReadModel;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookSectionWriteModel;
import com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book.BookWriteModel;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    SectionService sectionService;
    BookRepository repository;
    AuthorService authorService;
    BookCopyService bookCopyService;
    public BookService(final BookRepository repository, final AuthorService authorService, final BookCopyService bookCopyService,
                       final SectionService sectionService) {
        this.sectionService = sectionService;
        this.bookCopyService= bookCopyService;
        this.authorService = authorService;
        this.repository = repository;
    }

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
        if (section.getId() == null) {
            int id= sectionService.createSectionAndGetId(book, section);
            section.setId(id);
        }
        book.setSection(sectionService.findSection(section.getId()));

        toCreate.getAuthors().stream()
                .filter(author -> author.getId() == null)
                .forEach(author -> {
                    var newAuthor = authorService.createAuthor(author, book);
                    author.setId(newAuthor.getId());
                });
        book.setAuthors(toCreate.getAuthors().stream()
                .map(author -> authorService.readAuthor(author.getId()))
                .collect(Collectors.toSet()));
    }


}
