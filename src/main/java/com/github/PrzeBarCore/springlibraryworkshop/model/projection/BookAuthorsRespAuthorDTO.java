package com.github.PrzeBarCore.springlibraryworkshop.model.projection;
import com.github.PrzeBarCore.springlibraryworkshop.model.Author;

public class BookAuthorsRespAuthorDTO {
    private int id;
    private String name;
    private String lastName;

    public BookAuthorsRespAuthorDTO(Author source){
        this.id=source.getId();
        this.name=source.getName();
        this.lastName= source.getLastName();
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
