package com.github.PrzeBarCore.springlibraryworkshop.model.projection.Book;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

public class BookCopyPublisherWriteModel {
    private Integer id;
    private String name;

    public BookCopyPublisherWriteModel(String name) {
        this.name = name;
    }

    public BookCopyPublisherWriteModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Publisher toPublisher(BookCopy bookCopy){
        return new Publisher(name,bookCopy);
    }
}
