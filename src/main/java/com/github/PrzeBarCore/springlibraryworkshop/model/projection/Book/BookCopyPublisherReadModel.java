package com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

public class BookCopyPublisherReadModel {
    String name;

    BookCopyPublisherReadModel(Publisher source){
        this.name= source.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
