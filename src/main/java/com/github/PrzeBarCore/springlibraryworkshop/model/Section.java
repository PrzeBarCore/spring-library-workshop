package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forSectionName;

@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Section's name cannot be empty")
    @Pattern(regexp = forSectionName, message = "Section's name is invalid")
    private String name;

    @OneToMany(mappedBy = "section")
    private Set<Book> books=new HashSet<>();

    public Section(){}

    public Section(String name, Book book) {
        this.name=name;
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

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
