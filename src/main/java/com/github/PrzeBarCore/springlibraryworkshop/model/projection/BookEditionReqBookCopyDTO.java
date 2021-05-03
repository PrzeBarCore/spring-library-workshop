package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;
import com.github.PrzeBarCore.springlibraryworkshop.model.BookEdition;

import javax.validation.constraints.NotNull;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.BookState.*;

public class BookEditionReqBookCopyDTO {
    @NotNull(message = "Copy's id cannot be null")
    private Integer id;

    private String state;
    private Boolean isNewCopy;

    public BookEditionReqBookCopyDTO() {
        this.id =0;
        this.state = AVAILABLE.toString();
        this.isNewCopy =true;
    }

    public BookEditionReqBookCopyDTO(BookCopy source){
        this.id=source.getId();
        state=source.getState().toString();
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

    public void setState(String state) { this.state = state; }

    public boolean isNewCopy() {
        return isNewCopy;
    }

    public void setNewCopy(boolean newCopy) {
        isNewCopy = newCopy;
    }

    public BookCopy toBookCopy(BookEdition bookEdition){
        var bookCopy=new BookCopy();
        bookCopy.setId(id);
        bookCopy.setBookEdition(bookEdition);
        if(state.equals(AVAILABLE.toString())) {
            bookCopy.setState(AVAILABLE);
        }else if(state.equals(BORROWED.toString())){
            bookCopy.setState(BORROWED);
        }else {
            bookCopy.setState(RESERVED);
        }
        return bookCopy;
    }
}
