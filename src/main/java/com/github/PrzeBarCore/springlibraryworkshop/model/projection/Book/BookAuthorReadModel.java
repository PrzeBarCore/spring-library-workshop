package com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;

public class BookAuthorReadModel {
    private String name;
    private String lastName;

    BookAuthorReadModel(Author source){
        this.name=source.getName();
        this.lastName= source.getLastName();
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
}
