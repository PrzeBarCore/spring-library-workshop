package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookRespBookEditionDTO {
    @NotNull(message = "Edition's id cannot be null")
    private Integer id;
    private Year publicationDate;
    @Valid
    @NotNull(message = "Edition's publisher cannot be null")
    private BookPublishersRespPublisherDTO publisher;
    @NotEmpty(message = "Edition's copies cannot be empty")
    private List<@Valid BookRespBookCopyDTO> bookCopies;

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
}

