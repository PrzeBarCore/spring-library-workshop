package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import java.util.Set;
import java.util.stream.Collectors;

public class BookRespBookDTO {

    private int id;
    private String title;
    private BookSectionsRespSectionDTO section;
    private Set<BookRespBookEditionDTO> bookEditions;
    private Set<BookAuthorsRespAuthorDTO> authors;

    public BookRespBookDTO(){}

    public BookRespBookDTO(Book source){
        this.id=source.getId();
        this.title=source.getTitle();
        this.section= new BookSectionsRespSectionDTO(source.getSection());
        this.bookEditions = source.getBookEditions().stream().map(BookRespBookEditionDTO::new).collect(Collectors.toSet());
        this.authors=source.getAuthors().stream().map(BookAuthorsRespAuthorDTO::new).collect(Collectors.toSet());
    }

    public int getId() {return id;}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookSectionsRespSectionDTO getSection() {
        return section;
    }

    public void setSection(BookSectionsRespSectionDTO section) {
        this.section = section;
    }

    public Set<BookRespBookEditionDTO> getBookEditions() {
        return bookEditions;
    }

    public void setBookEditions(Set<BookRespBookEditionDTO> bookEditions) {
        this.bookEditions = bookEditions;
    }

    public Set<BookAuthorsRespAuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<BookAuthorsRespAuthorDTO> authors) {
        this.authors = authors;
    }
}
