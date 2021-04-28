package com.github.PrzeBarCore.springlibraryworkshop.model.projection;


import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forBookTitle;

public class BookReqBookDTO {
    @NotBlank(message = "Book's title cannot be empty")
    @Pattern(regexp = forBookTitle, message = "Book's title is invalid")
    private String title;

    @NotNull
    @Valid
    private BookReqSectionDTO section;

    @NotEmpty
    private List<@Valid BookReqBookEditionDTO> bookEditions=new ArrayList<>();

    private List<@Valid BookReqAuthorDTO> authors=new ArrayList<>();

    private List<BookReqAuthorDTO> authorsToRemove=new ArrayList<>();

    public BookReqBookDTO() {
        this.bookEditions.add(new BookReqBookEditionDTO());
    }

    public BookReqBookDTO(Book source){
        this.title=source.getTitle();
        this.section=new BookReqSectionDTO(source.getSection());
        this.authors=source.getAuthors()
                .stream()
                .sorted(Comparator.comparing(Author::getLastName))
                .map(BookReqAuthorDTO::new)
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public BookReqSectionDTO getSection() {
        return section;
    }

    public void setSection(BookReqSectionDTO section) {
        this.section= section;
    }

    public List<BookReqBookEditionDTO> getBookEditions() {
        return bookEditions;
    }

    public void setBookEditions(List<BookReqBookEditionDTO> bookEditions) {
        this.bookEditions = bookEditions;
    }

    public List<BookReqAuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<BookReqAuthorDTO> authors) {
        this.authors = authors;
    }

    public void addAuthor(){
        this.authors.add(new BookReqAuthorDTO());
    }

    public List<BookReqAuthorDTO> getAuthorsToRemove() {
        return authorsToRemove;
    }

    public void setAuthorsToRemove(List<BookReqAuthorDTO> authorsToRemove) {
        this.authorsToRemove = authorsToRemove;
    }

    public void removeAuthor(int index){
        this.authorsToRemove.add(this.authors.get(index));
        this.authors.remove(index);
    }

    public void addBookEdition(){
        this.bookEditions.add(new BookReqBookEditionDTO());
    }

    public void removeBookEdition(int index){
        this.bookEditions.remove(index);
    }
}
