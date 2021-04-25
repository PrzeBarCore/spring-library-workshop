package com.github.PrzeBarCore.springlibraryworkshop.model.projection;


import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookReqBookDTO {
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Section cannot be empty")
    private BookReqSectionDTO section;
    @NotNull(message = "Section cannot be empty")
    private List<BookReqBookEditionDTO> bookEditions;
    private List<BookReqAuthorDTO> authors;
    private List<BookReqAuthorDTO> authorsToRemove;

    public BookReqBookDTO() {
        this.bookEditions = new ArrayList<>();
        this.bookEditions.add(new BookReqBookEditionDTO());
        this.authors= new ArrayList<>();
        this.authorsToRemove=new ArrayList<>();
    }

    public void addAuthor(){

        this.authors.add(new BookReqAuthorDTO());
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

    public List<BookReqAuthorDTO> getAuthorsToRemove() {
        return authorsToRemove;
    }

    public void setAuthorsToRemove(List<BookReqAuthorDTO> authorsToRemove) {
        this.authorsToRemove = authorsToRemove;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static BookReqBookDTO fromBook(Book source){
        var bookWriteModel= new BookReqBookDTO();
        bookWriteModel.title=source.getTitle();
        bookWriteModel.section=BookReqSectionDTO.fromSection(source.getSection());
        bookWriteModel.authors=source.getAuthors()
                .stream()
                .sorted(Comparator.comparing(Author::getLastName))
                .map(BookReqAuthorDTO::fromAuthor)
                .collect(Collectors.toList());
        return bookWriteModel;
    }

}
