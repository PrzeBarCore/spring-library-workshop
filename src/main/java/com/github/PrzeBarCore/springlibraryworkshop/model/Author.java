package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String lastName;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.REMOVE)
    private Set<Book> books= new HashSet<>();

    public Author(){};

    public Author(String name, String lastName, Book book) {
        this.name=name;
        this.lastName=lastName;
        this.books.add(book);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(final Set<Book> books) {
        this.books = books;
    }
}
