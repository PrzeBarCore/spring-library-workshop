package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookRespBookEditionDTO {
    private int id;
    private int publicationDate;
    private BookPublishersRespPublisherDTO publisher;
    private List<BookRespBookCopyDTO> bookCopies;


    BookRespBookEditionDTO(BookEdition source) {
         this.id=source.getId();
         this.bookCopies= source.getBookCopies()
                 .stream()
                 .sorted(Comparator.comparing(BookCopy::getId))
                 .map(BookRespBookCopyDTO::new)
                 .collect(Collectors.toList());
         this.publicationDate = source.getPublicationDate();
        this.publisher = new BookPublishersRespPublisherDTO(source.getPublisher());
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BookPublishersRespPublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(BookPublishersRespPublisherDTO publisher) {
        this.publisher = publisher;
    }

    public List<BookRespBookCopyDTO> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookRespBookCopyDTO> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

