package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Publication date cannot be empty")
    private int publicationDate;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name= "section_id")
    private Section section;

    @ManyToMany
    @JoinTable(name = "books_authors",
    joinColumns = @JoinColumn(name="book_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    Book(){

    }


    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    void setPublicationDate(final int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    void setPublisherId(final Publisher publisher) {
        this.publisher = publisher;
    }

    public Section getSection() {
        return section;
    }

    void setSection(final Section section) {
        this.section = section;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    void setAuthors(final Set<Author> authors) {
        this.authors = authors;
    }
}
