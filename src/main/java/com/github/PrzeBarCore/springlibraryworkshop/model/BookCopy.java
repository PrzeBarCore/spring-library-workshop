package com.github.PrzeBarCore.springlibraryworkshop.model;

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
    private Integer state;

    @NotNull
    @ManyToOne
    @JoinColumn(name="book_edition_id")
    private BookEdition bookEdition;

    private LocalDateTime borrowedUntil;
    private LocalDateTime reservedUntil;

    public BookCopy(){
        this.state=0;
    }

    public BookCopy(BookEdition bookEdition){
        this.state=0;
        this.bookEdition=bookEdition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
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
