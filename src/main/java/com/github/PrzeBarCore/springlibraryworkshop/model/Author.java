package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forAuthorLastName;
import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forAuthorName;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Author's name cannot be empty")
    @Size(min = 1, max = 40)
    @Pattern(regexp = forAuthorName, message = "Author's name is invalid")
    private String name;

    @Size(max = 40)
    @Pattern(regexp = forAuthorLastName, message = "Author's last name is invalid")
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books= new HashSet<>();

    public Author(){}

    public Author(String name, String lastName, Book book) {
        this.name=name;
        this.lastName=lastName;
        this.books.add(book);
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
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
