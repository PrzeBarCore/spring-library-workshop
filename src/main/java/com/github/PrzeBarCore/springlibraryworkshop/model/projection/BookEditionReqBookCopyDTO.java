package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

import javax.validation.constraints.NotNull;

public class BookEditionReqBookCopyDTO {
    @NotNull(message = "Copy's id cannot be null")
    private Integer id;

    private String state;
    private Boolean isNewCopy;

    public BookEditionReqBookCopyDTO() {
        this.id =0;
        this.state = "Available";
        this.isNewCopy =true;
    }

    public BookEditionReqBookCopyDTO(BookCopy source){
        this.id=source.getId();
        int stateOfBookCopy=source.getState();
        if(stateOfBookCopy==0){
            this.state="Available";
        } else if(stateOfBookCopy==1){
            this.state="Borrowed";
        } else{
            this.state="Reserved";
        }
        this.isNewCopy =false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isNewCopy() {
        return isNewCopy;
    }

    public void setNewCopy(boolean newCopy) {
        isNewCopy = newCopy;
    }
}
