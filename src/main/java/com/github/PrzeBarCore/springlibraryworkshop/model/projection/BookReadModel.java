package com.github.PrzeBarCore.springlibraryworkshop.model.projection;

import com.github.PrzeBarCore.springlibraryworkshop.model.Book;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class BookReadModel
{
    private String title;
    private int publicationDate;
    private BookPublisherReadModel publisher;
    private BookSectionReadModel section;
    private Set<BookAuthorReadModel> authors;

    public BookReadModel(Book source) {
        this.title = source.getTitle();
        this.publicationDate = source.getPublicationDate();
        this.publisher=new BookPublisherReadModel(source.getPublisher());
        this.section = new BookSectionReadModel(source.getSection());
        this.authors = source.getAuthors()
                .stream()
                .map(BookAuthorReadModel::new)
                .collect(toSet());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(final int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BookPublisherReadModel getPublisher() {
        return publisher;
    }

    public void setPublisher(final BookPublisherReadModel publisher) {
        this.publisher = publisher;
    }

    public BookSectionReadModel getSection() {
        return section;
    }

    public void setSection(final BookSectionReadModel section) {
        this.section = section;
    }

    public Set<BookAuthorReadModel> getAuthors() {
        return authors;
    }

    public void setAuthors(final Set<BookAuthorReadModel> authors) {
        this.authors = authors;
    }
}
