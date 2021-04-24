package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

public class BookReqPublisherDTO {
    private Integer id=0;
    private String name;

    public BookReqPublisherDTO() {
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

    public Publisher toPublisher(BookEdition bookCopy){
        return new Publisher(name,bookCopy);
    }

    public static BookReqPublisherDTO fromPublisher(Publisher source){
        var result=new BookReqPublisherDTO();
        result.id=source.getId();
        result.name= source.getName();
        return result;
    }
}
