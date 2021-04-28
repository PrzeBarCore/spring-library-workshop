package com.github.PrzeBarCore.springlibraryworkshop.model;

import com.github.PrzeBarCore.springlibraryworkshop.utils.YearAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_editions")
public class BookEdition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "smallint")
    @Convert(converter = YearAttributeConverter.class)
    @PastOrPresent
    private Year publicationDate;

    @Size(max = 12)
    @OneToMany(mappedBy = "bookEdition", cascade = CascadeType.ALL)
    private Set<BookCopy> bookCopies= new HashSet<>();

    @NotNull(message = "Edition's publisher cannot be empty")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @NotNull(message = "Edition's book cannot be empty")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Year getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Year publicationDate) {
        if(publicationDate.isAfter(Year.now())){
            throw new IllegalStateException("Publication year cannot be after current year");
        } else if(publicationDate.isBefore(Year.of(1900))){
            throw new IllegalStateException("Cannot add book which was published before 1900");
        }
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
