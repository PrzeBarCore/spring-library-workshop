package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;
import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

import javax.validation.constraints.NotNull;

public class BookReqPublisherDTO {
    @NotNull(message = "Publisher's id cannot be blank")
    private Integer id=0;
    private String name;
    private Boolean isNewPublisher;

    public BookReqPublisherDTO() {
        this.isNewPublisher =false;
    }

    public BookReqPublisherDTO(Publisher source){
        this.id=source.getId();
        this.name=source.getName();
        this.isNewPublisher=false;
    }

    public Integer getId() { return id; }

    public void setId( Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNewPublisher() {return isNewPublisher;}

    public void setNewPublisher(boolean newPublisher) {
        this.isNewPublisher = newPublisher;
    }

    public Publisher toPublisher(BookEdition bookCopy){
        String trimmedName=name.trim();
        if(isNewPublisher && trimmedName.isBlank()){
            throw new IllegalArgumentException("Publisher's name cannot be blank");
        }
        return new Publisher(trimmedName,bookCopy);
    }
}
