package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

public class AuthorSectionPublisherRespBookDTO {
    private int id;
    private String title;

    public AuthorSectionPublisherRespBookDTO(Book source){
        this.id= source.getId();
        this.title= source.getTitle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
