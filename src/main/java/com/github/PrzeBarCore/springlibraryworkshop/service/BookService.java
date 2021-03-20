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
        var book = new Book(toCreate.getTitle());

        BookSectionWriteModel section= toCreate.getSection();
        if (section.getId() == null) {
           int id= sectionService.createSectionAndGetId(book, section);
           section.setId(id);
        }
        book.setSection(sectionService.findSection(section.getId()));


        Set<BookCopy> createdBookCopies= new HashSet<>();
        toCreate.getBookCopies()
                .forEach(bookCopy -> createdBookCopies.add(bookCopyService.createBookCopy(bookCopy, book)));
        book.setBookCopies(createdBookCopies);

        toCreate.getAuthors().stream()
                .filter(author -> author.getId() == null)
                .forEach(author -> {
                    var newAuthor = authorService.createAuthor(author, book);
                    author.setId(newAuthor.getId());
                });
        book.setAuthors(toCreate.getAuthors().stream()
                .map(author -> authorService.readAuthor(author.getId()))
                .collect(Collectors.toSet()));
        var result=repository.save(book);
        return new BookReadModel(result);
    }

    public void deleteBook(Integer id) {
        if(!repository.existsBookById(id)){
            throw new IllegalArgumentException("Book with given ID doesn't exist");
        }

        repository.deleteBookById(id);
    }

    public BookReadModel readBook(Integer id) {
       BookReadModel result= repository.findById(id)
               .map(BookReadModel::new)
               .orElseThrow(() ->new IllegalArgumentException("Book with given ID doesn't exist"));
       return result;
    }




//    public List<BookReadModel> findAll(Optional<Integer> pageNumber, Optional<Integer> pageSize){
//
//        Pageable pageable= PageRequest.of(pageNumber.orElse(0), pageSize.orElse(10));
//        return repository.findAll(pageable).stream().map(BookReadModel::new).collect(toList());
//    }
}
