package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorRespAuthorDTO {
    private String name;
    private String lastName;
    private List<AuthorSectionPublisherRespBookDTO> books;

    public AuthorRespAuthorDTO(Author source){
        this.name= source.getName();
        this.lastName= source.getLastName();
        this.books=source.getBooks().stream()
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AuthorSectionPublisherRespBookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<AuthorSectionPublisherRespBookDTO> books) {
        this.books = books;
    }
}
