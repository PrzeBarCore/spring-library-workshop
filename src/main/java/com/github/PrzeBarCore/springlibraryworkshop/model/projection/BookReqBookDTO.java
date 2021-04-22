package com.github.PrzeBarCore.springlibraryworkshop.model.projection;


import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookReqBookDTO {
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Section cannot be empty")
    private BookReqSectionDTO section;

    private List<BookReqBookEditionDTO> bookEditions;

    private List<BookReqAuthorDTO> authors;


    public BookReqBookDTO() {
        this.bookEditions = new ArrayList<>();
        this.bookEditions.add(new BookReqBookEditionDTO());
        this.authors= new ArrayList<>();

    }


    public void addAuthor(){

        this.authors.add(new BookReqAuthorDTO());
    }

    public void removeAuthor(int index){
        this.authors.remove(index);

    }

    public void addBookEdition(){
        this.bookEditions.add(new BookReqBookEditionDTO());

    }

    public void removeBookEdition(int index){
        this.bookEditions.remove(index);

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
                .map(BookReqAuthorDTO::fromAuthor)
                .collect(Collectors.toList());
        return bookWriteModel;
    }

    public Book toBook(){
        var book= new Book(title);
        book.setSection(section.toSection(book));
        book.setBookEditions(bookEditions.stream().map(bookCopy-> bookCopy.toBookEdition(book)).collect(Collectors.toUnmodifiableSet()));
        book.setAuthors(authors.stream().map(author -> author.toAuthor(book)).collect(Collectors.toUnmodifiableSet()));

        return book;
    }

}
