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

    @OneToMany(mappedBy = "publisher")
    private Set<BookEdition> bookEditions =new HashSet<>();

    public Publisher(){}

    public Publisher(String name, BookEdition bookEdition) {
        this.name=name;
        this.bookEditions.add(bookEdition);
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

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookEdition> getBookEditions() {
        return bookEditions;
    }

    void setBookEditions(final Set<BookEdition> books) {
        this.bookEditions = books;
    }


}
