package com.github.PrzeBarCore.springlibraryworkshop.model;

import com.github.PrzeBarCore.springlibraryworkshop.utils.BookState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="book_copies")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private BookState state;

    @NotNull
    @ManyToOne
    @JoinColumn(name="book_edition_id")
    private BookEdition bookEdition;

    private LocalDateTime borrowedUntil;
    private LocalDateTime reservedUntil;

    public BookCopy(){
        this(null);
    }

    public BookCopy(BookEdition bookEdition){
        this.state=BookState.AVAILABLE;
        this.bookEdition=bookEdition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public BookEdition getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(BookEdition bookEdition) {
        this.bookEdition = bookEdition;
    }

    public LocalDateTime getBorrowedUntil() {
        return borrowedUntil;
    }

    public void setBorrowedUntil(LocalDateTime borrowedUntil) {
        this.borrowedUntil = borrowedUntil;
    }

    public LocalDateTime getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(LocalDateTime reservedUntil) {
        this.reservedUntil = reservedUntil;
    }
}
