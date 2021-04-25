package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import java.util.HashSet;
import java.util.Set;

public class BookReqBookEditionDTO {

    private int id;
    private int publicationDate;
    private BookReqPublisherDTO publisher;
    private boolean isNewPublisher;
    private int numberOfCopies;

    public BookReqBookEditionDTO() {
        this.publisher= new BookReqPublisherDTO();
        this.publicationDate=2000;
        this.isNewPublisher =false;
        this.numberOfCopies =1;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public boolean isNewPublisher() {
        return isNewPublisher;
    }

    public void setNewPublisher(boolean newPublisher) {
        this.isNewPublisher = newPublisher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BookReqPublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(BookReqPublisherDTO publisher) {
        this.publisher = publisher;
    }

    private Set<BookCopy> createSetOfBookCopies(int numberOfCopies, BookEdition bookEdition){
        Set<BookCopy> bookCopies=new HashSet<>();
        for(int i=0; i<numberOfCopies; i++){
            bookCopies.add(new BookCopy(bookEdition));
        }
        return bookCopies;
    }

    public BookEdition toBookEdition(Book book){
        var bookEdition=new BookEdition();
        bookEdition.setPublicationDate(publicationDate);
        bookEdition.setBook(book);
        bookEdition.setBookCopies(createSetOfBookCopies(this.numberOfCopies, bookEdition));
        return bookEdition;
    }
}
