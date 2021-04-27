package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Publisher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookPublishersRespPublisherDTO {
    @NotNull(message = "Publisher's id cannot be null")
    Integer id;
    @NotBlank(message = "Publisher's name cannot be blank")
    String name;

    public BookPublishersRespPublisherDTO(Publisher source){
        this.id=source.getId();
        this.name= source.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
