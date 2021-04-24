package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

public class BookEditionReqBookCopyDTO {
    private int id;
    private String state;
    private boolean isNewCopy;
    public BookEditionReqBookCopyDTO() {
        this.id = -1;
        this.state = "Available";
        this.isNewCopy =true;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public static BookEditionReqBookCopyDTO fromBookCopy(BookCopy source){
        var result= new BookEditionReqBookCopyDTO();
        result.id=source.getId();
        int stateOfBookCopy=source.getState();
        if(stateOfBookCopy==0){
            result.state="Available";
        } else if(stateOfBookCopy==1){
            result.state="Borrowed";
        } else{
            result.state="Reserved";
        }
        result.isNewCopy =false;
        return result;
    }
}
