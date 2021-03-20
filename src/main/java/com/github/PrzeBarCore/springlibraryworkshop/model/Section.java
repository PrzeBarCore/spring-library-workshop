package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany(mappedBy = "section")
    private Set<Book> books=new HashSet<>();

    public Section(){
    }

    public Section(String name, Book book) {
        this.name=name;
        this.books.add(book);
    }

    public Integer getId() {
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
}
