package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forPublisherName;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Publisher's name cannot be empty")
    @Pattern(regexp = forPublisherName, message = "Publisher's name is invalid")
    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<BookEdition> bookEditions =new HashSet<>();

    public Publisher(){}

    public Publisher(String name, BookEdition bookEdition) {
        this.name=name;
        this.bookEditions.add(bookEdition);
    }

    public Integer getId() {
        return id;
    }

    void setId(final Integer id) {
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
