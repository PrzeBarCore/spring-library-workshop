package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.BookCopy;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class BookRespBookCopyDTO {
    @NotNull(message = "Copy's id cannot be null")
    private Integer id;

    private String state;
    private String borrowedUntil;
    private String reservedUntil;

    public BookRespBookCopyDTO(BookCopy source) {
        this.id = source.getId();
        state = source.getState().toString();
        this.borrowedUntil = Optional.ofNullable(
                source.getBorrowedUntil())
                .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orElse("");
        this.reservedUntil = Optional.ofNullable(
                source.getReservedUntil())
                .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orElse("");
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

    public String getBorrowedUntil() {
        return borrowedUntil;
    }

    public void setBorrowedUntil(String borrowedUntil) {
        this.borrowedUntil = borrowedUntil;
    }

    public String getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(String reservedUntil) {
        this.reservedUntil = reservedUntil;
    }
}


