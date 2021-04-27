package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookRespBookDTO {
    @NotNull(message = "Book's id cannot be null")
    private Integer id;
    @NotBlank(message = "Book's name cannot be blank")
    private String title;
    @Valid
    @NotNull(message = "Book's section cannot be null")
    private BookSectionsRespSectionDTO section;
    @NotNull(message = "Book's editions cannot be null")
    private List<@Valid BookRespBookEditionDTO> bookEditions;
    private List<@Valid BookAuthorsRespAuthorDTO> authors;

    public BookRespBookDTO(Book source){
        this.id=source.getId();
        this.title=source.getTitle();
        this.section= new BookSectionsRespSectionDTO(source.getSection());
        this.bookEditions = source.getBookEditions()
                .stream()
                .map(BookRespBookEditionDTO::new)
                .sorted(Comparator.comparing(BookRespBookEditionDTO::getId))
                .collect(Collectors.toList());
        this.authors=source.getAuthors()
                .stream()
                .map(BookAuthorsRespAuthorDTO::new)
                .sorted(Comparator.comparing(BookAuthorsRespAuthorDTO::getLastName))
                .collect(Collectors.toList());
    }

    public void setId(Integer id) {this.id = id; }

    public Integer getId() {return id;}

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

    public List<BookRespBookEditionDTO> getBookEditions() {
        return bookEditions;
    }

    public void setBookEditions(List<BookRespBookEditionDTO> bookEditions) {
        this.bookEditions = bookEditions;
    }

    public List<BookAuthorsRespAuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<BookAuthorsRespAuthorDTO> authors) {
        this.authors = authors;
    }
}
