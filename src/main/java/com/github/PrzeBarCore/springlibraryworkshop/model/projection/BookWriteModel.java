package com.github.PrzeBarCore.springlibraryworkshop.model.projection;


import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookWriteModel {
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Section cannot be empty")
    private BookSectionWriteModel section;

    private List<BookBookCopyWriteModel> bookCopies;

    private List<BookAuthorWriteModel> authors;


    public BookWriteModel() {


        this.bookCopies= new ArrayList<>();
        this.bookCopies.add(new BookBookCopyWriteModel());
        this.authors= new ArrayList<>();

    }

    public void addAuthor(){
        this.authors.add(new BookAuthorWriteModel());
    }

    public void removeAuthor(int index){
        this.authors.remove(index);

    }

    public void addBookCopy(){
        this.bookCopies.add(new BookBookCopyWriteModel());

    }

    public void removeBookCopy(int index){
        this.bookCopies.remove(index);

    }

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

    public List<BookBookCopyWriteModel> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookBookCopyWriteModel> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public List<BookAuthorWriteModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<BookAuthorWriteModel> authors) {
        this.authors = authors;
    }


    public Book toBook(){
        var book= new Book(title);
        book.setSection(section.toSection(book));
        book.setBookCopies(bookCopies.stream().map(bookCopy-> bookCopy.toBookCopy(book)).collect(Collectors.toUnmodifiableSet()));
        book.setAuthors(authors.stream().map(author -> author.toAuthor(book)).collect(Collectors.toUnmodifiableSet()));

        return book;
    }

    public class BookCopyCheckBox{
        private boolean state;

        public BookCopyCheckBox(boolean state) {
            this.state = state;
        }

        public BookCopyCheckBox() {
            this.state = false;
        }

        public boolean getState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }

}
