package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorReqRespAuthorDTO {

    @NotNull(message = "Author's id cannot be null")
    private int id;
    @NotEmpty(message = "Author's name cannot be empty")
    private String name;
    private String lastName;
    @NotNull(message = "Author's books cannot be null")
    private List<AuthorSectionPublisherRespBookDTO> books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static AuthorReqRespAuthorDTO fromAuthor(Author author){
        var authorDTO= new AuthorReqRespAuthorDTO();
        authorDTO.id= author.getId();
        authorDTO.name= author.getName();
        authorDTO.lastName= author.getLastName();
        authorDTO.books=author.getBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());
        return authorDTO;
    }

    public Author updateAuthorFromDTO(Author author){
        author.setName(this.name);
        author.setLastName(this.lastName);
        return author;
    }
}
