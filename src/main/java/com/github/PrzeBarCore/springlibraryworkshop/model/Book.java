package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private int publicationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "section_id")
    private Section section;

    @ManyToMany
    @JoinTable(name = "books_authors",
    joinColumns = @JoinColumn(name="book_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;



    int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    int getPublicationDate() {
        return publicationDate;
    }

    void setPublicationDate(final int publicationDate) {
        this.publicationDate = publicationDate;
    }

    Publisher getPublisher() {
        return publisher;
    }

    void setPublisherId(final Publisher publisher) {
        this.publisher = publisher;
    }

    Section getSection() {
        return section;
    }

    void setSection(final Section section) {
        this.section = section;
    }
}
