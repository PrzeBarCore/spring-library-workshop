package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

public class BookReqBookEditionDTO {
    @NotNull
    private Integer id;

    @PastOrPresent(message = "The date of issue cannot be later than the current one")
    private Year publicationDate;

    @NotNull(message = "Edition's publisher cannot be null")
    @Valid
    private BookReqPublisherDTO publisher;

    @Positive(message = "Copies count must be positive")
    @Max(value = 12, message = "Cannot add more than 12 copies")
    private Integer numberOfCopies;

    public BookReqBookEditionDTO() {
        this.id=0;
        this.publisher= new BookReqPublisherDTO();
        this.publicationDate=Year.now();
        this.numberOfCopies =1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Year getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Year publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BookReqPublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(BookReqPublisherDTO publisher) {
        this.publisher = publisher;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    private Set<BookCopy> createSetOfBookCopies(int numberOfCopies, BookEdition bookEdition){
        Set<BookCopy> bookCopies=new HashSet<>();
        for(int i=0; i<numberOfCopies; i++){
            bookCopies.add(new BookCopy(bookEdition));
        }
        return bookCopies;
    }

    public BookEdition toBookEdition(Book book){
        if(publicationDate.isAfter(Year.now())){
            throw new IllegalArgumentException("The date of issue cannot be later than the current one");
        } else if(publicationDate.isBefore(Year.of(1900))){
            throw new IllegalArgumentException("Cannot add book which was published before 1900");
        }

        var bookEdition=new BookEdition();
        bookEdition.setPublicationDate(publicationDate);
        bookEdition.setBook(book);
        bookEdition.setBookCopies(createSetOfBookCopies(this.numberOfCopies, bookEdition));
        return bookEdition;
    }
}
