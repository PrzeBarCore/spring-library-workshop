package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

public class BookBookCopyWriteModel {

    private int id;
    private int state;
    private int publicationDate;
    private BookCopyPublisherWriteModel publisher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BookCopyPublisherWriteModel getPublisher() {
        return publisher;
    }

    public void setPublisher(BookCopyPublisherWriteModel publisher) {
        this.publisher = publisher;
    }

    public BookCopy toBookCopy(Book book){
        var bookCopy=new BookCopy();
        bookCopy.setState(state);
        bookCopy.setPublicationDate(publicationDate);
        bookCopy.setBook(book);
        bookCopy.setPublisher(publisher.toPublisher(bookCopy));
        return bookCopy;
    }
}
