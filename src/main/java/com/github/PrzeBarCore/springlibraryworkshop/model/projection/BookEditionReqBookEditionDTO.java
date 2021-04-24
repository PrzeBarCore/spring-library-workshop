package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookEditionReqBookEditionDTO {
    private int id;
    private int publicationDate;
    private BookReqPublisherDTO publisher;
    private boolean isNewPublisher;
    private List<BookEditionReqBookCopyDTO> bookCopies=new ArrayList<>();
    private List<BookEditionReqBookCopyDTO> bookCopiesToRemove =new ArrayList<>();

    public BookEditionReqBookEditionDTO(){}

    public List<BookEditionReqBookCopyDTO> getBookCopiesToRemove() {
        return bookCopiesToRemove;
    }

    public void setBookCopiesToRemove(List<BookEditionReqBookCopyDTO> bookCopiesToRemove) {
        this.bookCopiesToRemove = bookCopiesToRemove;
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

    public boolean isNewPublisher() {
        return isNewPublisher;
    }

    public void setNewPublisher(boolean newPublisher) {
        isNewPublisher = newPublisher;
    }

    public List<BookEditionReqBookCopyDTO> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookEditionReqBookCopyDTO> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public static BookEditionReqBookEditionDTO fromBookEdition(BookEdition source){
        var result=new BookEditionReqBookEditionDTO();
        result.id= source.getId();
        result.publicationDate= source.getPublicationDate();
        result.publisher=BookReqPublisherDTO.fromPublisher(source.getPublisher());
        result.isNewPublisher=false;
        result.bookCopies=source.getBookCopies()
                .stream()
                .sorted(Comparator.comparing(BookCopy::getId))
                .map(BookEditionReqBookCopyDTO::fromBookCopy)
                .collect(Collectors.toList());
        return result;
    }

    public void removeBookCopy(int copyToRemove) {
        bookCopiesToRemove.add(bookCopies.get(copyToRemove));
        bookCopies.remove(copyToRemove);
    }

    public void addBookCopy(){
        this.bookCopies.add(new BookEditionReqBookCopyDTO());
    }
}
