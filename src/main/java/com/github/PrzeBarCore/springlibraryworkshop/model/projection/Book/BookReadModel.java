package com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import java.util.Set;
import java.util.stream.Collectors;

public class BookReadModel {

    private String title;
    private BookSectionReadModel section;
    private Set<BookBookCopyReadModel> bookCopies;
    private Set<BookAuthorReadModel> authors;

    public BookReadModel(){}

    public BookReadModel(Book source){
        this.title=source.getTitle();
        this.section= new BookSectionReadModel(source.getSection());
        this.bookCopies= source.getBookCopies().stream().map(BookBookCopyReadModel::new).collect(Collectors.toSet());
        this.authors=source.getAuthors().stream().map(BookAuthorReadModel::new).collect(Collectors.toSet());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookSectionReadModel getSection() {
        return section;
    }

    public void setSection(BookSectionReadModel section) {
        this.section = section;
    }

    public Set<BookBookCopyReadModel> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(Set<BookBookCopyReadModel> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public Set<BookAuthorReadModel> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<BookAuthorReadModel> authors) {
        this.authors = authors;
    }
}
