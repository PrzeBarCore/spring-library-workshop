package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forBookTitle;

public class AuthorSectionPublisherRespBookDTO {
    @NotNull(message = "Book's id cannot be null")
    private Integer id;

    @NotBlank(message = "Book's title cannot be empty")
    @Pattern(regexp = forBookTitle, message = "Book's title is invalid")
    private String title;

    public AuthorSectionPublisherRespBookDTO() {
    }

    public AuthorSectionPublisherRespBookDTO(Book source){
        this.id= source.getId();
        this.title= source.getTitle();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
