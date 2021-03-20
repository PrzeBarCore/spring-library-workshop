package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    private Set<BookCopy> bookCopies=new HashSet<>();

    public Publisher(){

    }

    public Publisher(String name, BookCopy bookCopy) {
        this.name=name;
        this.bookCopies.add(bookCopy);
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public Set<BookCopy> getBookCopies() {
        return bookCopies;
    }

    void setBookCopies(final Set<BookCopy> books) {
        this.bookCopies = books;
    }
}
