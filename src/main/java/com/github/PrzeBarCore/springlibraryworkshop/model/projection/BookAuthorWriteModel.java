package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;
import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

public class BookAuthorWriteModel {
    private Integer id;
    private String name;
    private String lastName;

    BookAuthorWriteModel(String name){
        this(name,null);
    };

    BookAuthorWriteModel(String name, String lastName){
        this.name=name;
        this.lastName=lastName;
    };

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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
        this.lastName = lastName;
    }

    public Author toAuthor(final Book book){
        return new Author(name,lastName,book);
    }
}
