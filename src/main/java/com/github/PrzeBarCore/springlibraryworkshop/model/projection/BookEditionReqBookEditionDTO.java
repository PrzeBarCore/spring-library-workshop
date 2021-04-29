package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookEditionReqBookEditionDTO {
    private Integer id;

    @PastOrPresent(message = "The date of issue cannot be later than the current one")
    private Year publicationDate;

    @NotNull(message = "Edition's publisher cannot be null")
    @Valid
    private BookReqPublisherDTO publisher;

    @NotEmpty(message = "Edition's copies cannot be empty")
    private List<@Valid BookEditionReqBookCopyDTO> bookCopies=new ArrayList<>();
    private List<BookEditionReqBookCopyDTO> bookCopiesToRemove =new ArrayList<>();

    public BookEditionReqBookEditionDTO(){}

    public BookEditionReqBookEditionDTO(BookEdition source){
        this.id=source.getId();
        this.publicationDate=source.getPublicationDate();
        this.publisher=new BookReqPublisherDTO(source.getPublisher());
        this.bookCopies=source.getBookCopies()
                .stream()
                .sorted(Comparator.comparing(BookCopy::getId))
                .map(BookEditionReqBookCopyDTO::new)
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id) {
        this.id = id;
    }

    public Year getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Year publicationDate){
        this.publicationDate = publicationDate;
    }

    public BookReqPublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(BookReqPublisherDTO publisher) {
        this.publisher = publisher;
    }
    public List<BookEditionReqBookCopyDTO> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies( List<BookEditionReqBookCopyDTO> bookCopies) {
        this.bookCopies = bookCopies;
    }
    public List<BookEditionReqBookCopyDTO> getBookCopiesToRemove() {
        return bookCopiesToRemove;
    }

    public void setBookCopiesToRemove(List<BookEditionReqBookCopyDTO> bookCopiesToRemove) {
        this.bookCopiesToRemove = bookCopiesToRemove;
    }

    public void removeBookCopy(int copyToRemove) {
        if(bookCopies.stream().count()<2){
            throw new IllegalArgumentException("Cannot delete last copy of edition");
        } else {
            if(!bookCopies.get(copyToRemove).isNewCopy()){
                bookCopiesToRemove.add(bookCopies.get(copyToRemove));
            }
            bookCopies.remove(copyToRemove);
        }
    }

    public void addBookCopy(){
        this.bookCopies.add(new BookEditionReqBookCopyDTO());
    }

    public void verify(){
        if(publicationDate.isAfter(Year.now())){
            throw new IllegalArgumentException("The date of issue cannot be later than the current one");
        } else if(publicationDate.isBefore(Year.of(1900))){
            throw new IllegalArgumentException("Cannot add book which was published before 1900");
        }
    }
}
