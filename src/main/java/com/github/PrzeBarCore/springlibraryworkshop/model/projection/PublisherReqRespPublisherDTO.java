package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forPublisherName;

public class PublisherReqRespPublisherDTO {
    @NotNull(message = "Publisher's name cannot be null")
    private Integer id;

    @NotBlank(message = "Publisher's name cannot be blank")
    @Pattern(regexp = forPublisherName, message = "Publisher's name is invalid")
    private String name;

    private List<AuthorSectionPublisherRespBookDTO> books;

    public PublisherReqRespPublisherDTO(){}

    public PublisherReqRespPublisherDTO(Publisher source){
        this.id=source.getId();
        this.name=source.getName();
        this.books=source.getBookEditions()
                .stream()
                .map(BookEdition::getBook)
                .sorted(Comparator.comparing(Book::getTitle))
                .map(AuthorSectionPublisherRespBookDTO::new)
                .collect(Collectors.toList());;
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

    public List<AuthorSectionPublisherRespBookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<AuthorSectionPublisherRespBookDTO> books) {
        this.books = books;
    }

    public Publisher updatePublisherFromDTO(Publisher publisher){
        publisher.setName(this.name);
        return publisher;
    }
}
