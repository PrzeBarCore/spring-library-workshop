package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

public class BookPublishersRespPublisherDTO {
    int id;
    String name;

    public BookPublishersRespPublisherDTO(Publisher source){
        this.id=source.getId();
        this.name= source.getName();
    }

    public int getId() {
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
}
