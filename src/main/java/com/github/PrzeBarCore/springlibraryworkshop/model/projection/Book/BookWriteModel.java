package com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book;


import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

public class BookWriteModel {
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Section cannot be empty")
    private BookSectionWriteModel section;

    private Set<BookBookCopyWriteModel> bookCopies;

    private Set<BookAuthorWriteModel> authors;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookSectionWriteModel getSection() {
        return section;
    }

    public void setSection(BookSectionWriteModel section) {
        this.section = section;
    }

    public Set<BookBookCopyWriteModel> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(Set<BookBookCopyWriteModel> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public Set<BookAuthorWriteModel> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<BookAuthorWriteModel> authors) {
        this.authors = authors;
    }

    public Book toBook(){
        var book= new Book(title);
        book.setSection(section.toSection(book));
        book.setBookCopies(bookCopies.stream().map(bookCopy-> bookCopy.toBookCopy(book)).collect(Collectors.toUnmodifiableSet()));
        book.setAuthors(authors.stream().map(author -> author.toAuthor(book)).collect(Collectors.toUnmodifiableSet()));

        return book;
    }
}
