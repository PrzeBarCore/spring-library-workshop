package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherRespPublisherDTO {
    private String name;
    private List<AuthorSectionPublisherRespBookDTO> books;

    public PublisherRespPublisherDTO(Publisher source) {
        this.name = source.getName();
        this.books = source.getBookEditions()
                .stream()
                .map(bookEdition -> bookEdition.getBook())
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AuthorSectionPublisherRespBookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<AuthorSectionPublisherRespBookDTO> books) {
        this.books = books;
    }
}
