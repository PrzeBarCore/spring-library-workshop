package com.github.PrzeBarCore.springlibraryworkshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_editions")
public class BookEdition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int publicationDate;


    @OneToMany(mappedBy = "bookEdition", cascade = CascadeType.ALL)
    private Set<BookCopy> bookCopies= new HashSet<>();

    @NotNull(message = "Publisher cannot be empty")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @NotNull(message = "Book cannot be empty")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public BookEdition() {}

    public Set<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(Set<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
