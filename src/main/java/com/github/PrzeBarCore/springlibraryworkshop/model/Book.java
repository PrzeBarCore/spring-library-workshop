package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static com.github.PrzeBarCore.springlibraryworkshop.utils.EntitiesStringsPatterns.forBookTitle;

@Entity
@Table(name="books")
    public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Book's title cannot be empty")
    @Pattern(regexp = forBookTitle, message = "Book's title is invalid")
    private String title;

    @NotNull(message = "Book's section cannot be empty")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name= "section_id")
    private Section section;

    @OneToMany(mappedBy = "book", cascade ={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<BookEdition> bookEditions;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "books_authors",
    joinColumns = @JoinColumn(name="book_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    public Book(){}

    public Book(String title) {
        this.title=title;
    }

    public Integer getId() {
        return id;
    }

    void setId(final Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(final Section section) {
        this.section = section;
    }

    public Set<BookEdition> getBookEditions() {
        return bookEditions;
    }

    public void setBookEditions(final Set<BookEdition> bookEditions) {
        this.bookEditions = bookEditions;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(final Set<Author> authors) {
        this.authors = authors;
    }
}
