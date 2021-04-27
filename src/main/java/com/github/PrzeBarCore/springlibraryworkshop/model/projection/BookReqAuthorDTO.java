package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import javax.validation.constraints.NotNull;

public class BookReqAuthorDTO {
    @NotNull(message = "Author's id cannot be null")
    private Integer id;
    private String name;
    private String lastName;
    private Boolean isNewAuthor;

    public BookReqAuthorDTO(){
        this.id=0;
        this.isNewAuthor =false;
    }

    public BookReqAuthorDTO(Author source) {
        this.id = source.getId();
        this.name = source.getName();
        this.lastName = source.getLastName();
        this.isNewAuthor = false;
    }

    public Integer getId(){
        return id;
    }

    public void setId( Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName.trim();
    }

    public boolean isNewAuthor() {
        return isNewAuthor;
    }

    public void setNewAuthor(boolean newAuthor) {
        this.isNewAuthor = newAuthor;
    }

    public Author toAuthor(final Book book){
        String trimmedName=name.trim();
        if(isNewAuthor && trimmedName.isBlank()){
            throw new IllegalArgumentException("Author's name cannot be blank");
        }
        return new Author(trimmedName,lastName.trim(),book);
    }
}
