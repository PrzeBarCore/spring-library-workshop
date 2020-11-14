package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Author;

public class BookAuthorReadModel {
    private String name;
    private String lastName;

    public BookAuthorReadModel(final Author source) {
        this.name = source.getName();
        this.lastName = source.getLastName();
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
}
