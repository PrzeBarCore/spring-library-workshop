package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorReqRespAuthorDTO {
    @NotNull(message = "Author's id cannot be null")
    private Integer id;
    @NotBlank(message = "Author's name cannot be empty")
    private String name;
    private String lastName;
    private List<AuthorSectionPublisherRespBookDTO> books;

    public AuthorReqRespAuthorDTO(){}

    public AuthorReqRespAuthorDTO (Author author){
        this.id=author.getId();
        this.name=author.getName();
        this.lastName=author.getLastName();
        this.books=author.getBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public List<AuthorSectionPublisherRespBookDTO> getBooks() {
        return books;
    }

    public void setBooks( List<AuthorSectionPublisherRespBookDTO> books) {
        this.books = books;
    }

    public Author updateAuthorFromDTO(Author author){
        author.setName(this.name);
        author.setLastName(this.lastName);
        return author;
    }
}
